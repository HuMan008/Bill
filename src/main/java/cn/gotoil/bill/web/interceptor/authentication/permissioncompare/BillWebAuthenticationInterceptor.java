package cn.gotoil.bill.web.interceptor.authentication.permissioncompare;

import cn.gotoil.bill.config.property.BillProperties;
import cn.gotoil.bill.config.property.SecureProperties;
import cn.gotoil.bill.exception.BillException;
import cn.gotoil.bill.exception.CommonError;
import cn.gotoil.bill.model.BaseAdminUser;
import cn.gotoil.bill.web.annotation.HasPermision;
import cn.gotoil.bill.web.annotation.HasRole;
import cn.gotoil.bill.web.annotation.NeedLogin;
import cn.gotoil.bill.web.helper.ServletRequestHelper;
import cn.gotoil.bill.web.helper.TokenHelper;
import cn.gotoil.bill.web.services.AdminUserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

/**
 * web权限
 *
 * @author think <syj247@qq.com>、
 * @date 2019-4-3、11:28
 */
@Component
public class BillWebAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private BillProperties billProperties;

    @Autowired
    SecureProperties secureProperties;

    Method authKeyMethod;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        /**  如果有HashcompareAuthenticationInterceptor 拦截器  直接不处理 **/
        if (uri.startsWith(billProperties.getKeyOfHashCompareAuthenticationPathPrefix())) {
            return true;
        }

        /**  如果有NeedLogin = false 直接不处理 **/
        if (noNeedLoginValid(handler)) {
            return true;
        }

        String token = request.getHeader("gtToken");// 从 http 请求头中取出 token

        if (StringUtils.isEmpty(token)) {
            throw new BillException(CommonError.Need_Login);
        }

        //通过反射获取用户对象 。
        if (authKeyMethod == null) {
            synchronized (this) {
                if (authKeyMethod == null) {
                    authKeyMethod = Class.forName(secureProperties.getBillWebAuthenicationClass()).
                            getMethod(secureProperties.getBillWebAuthenicationMethod(), String.class);
                }
            }
        }
        if(authKeyMethod==null){
            throw new BillException(CommonError.SystemError);
        }

        String userId;
        try {
            userId = JWT.decode(token).getClaim("id").asString();
        } catch (JWTDecodeException j) {
            throw new BillException(CommonError.TokenError);
        }

        if(StringUtils.isEmpty(userId)){
            throw new BillException(CommonError.TokenError);
        }

        BaseAdminUser baseAdminUser = (BaseAdminUser) authKeyMethod.invoke(null,userId);

        //token  校验
        Boolean verify =false;
        try{
            verify = TokenHelper.isVerify(token, baseAdminUser);
        }catch (Exception e){
            throw new BillException(CommonError.TokenError);
        }
        if (!verify) {
            throw new BillException(CommonError.TokenError);
        }

//        token过期
        if(Calendar.getInstance().getTime().getTime()-JWT.decode(token).getExpiresAt().getTime()>0){
            throw new BillException(CommonError.TokenError);
        }


        Claims claims=null;
        try{
            claims = TokenHelper.parseJWT(token, baseAdminUser);

        }catch (Exception e){
            throw new BillException(CommonError.TokenError);

        }

        HasPermision permissionHandler = ((HandlerMethod) handler).getMethodAnnotation(HasPermision.class);
        HasRole roleHander = ((HandlerMethod) handler).getMethodAnnotation(HasRole.class);

        boolean vv = true;
        //如果没有写haspermission 也没写hasRole
        if(permissionHandler==null && roleHander ==null){
            vv= true;
        }
        //hasrole里有货
        if(roleHander!=null){
            vv=userRoleAuthValidate(request,roleHander,claims.get("roleStr",String.class));
        }
        //hasPermission里有货
        if(permissionHandler!=null){
            vv = vv&& userPermissionAuthValidate(request,permissionHandler,claims.get("permissionStr",String.class));
        }
        if(!vv){
            throw new BillException(CommonError.PERMISSION_ERROR);
        }
        return vv;

    }



    /**
     * 是不是不需要登录
     * 如果写了注解，且值等于false的时候 才返回true 不需要登录
     * 其他都为需要登录
     *
     * @param handler
     * @return
     */
    private boolean noNeedLoginValid(Object handler) {
        NeedLogin hander = ((HandlerMethod) handler).getMethodAnnotation(NeedLogin.class);
        //如果没写是否要登录，那就是要登录
        if (hander == null || hander.value()) {
            return false;
        } else if (hander != null && hander.value() == false) {
            return true;
        } else {
            return false;
        }

    }

    private boolean userPermissionAuthValidate(HttpServletRequest request, HasPermision permisisonHander,String permissionStr) {

        //如果没写权限要求，那就直接验证通过
        if (permisisonHander == null) {
            return true;
        }

        if (StringUtils.isEmpty(permisisonHander.value()) && permisisonHander.values().length == 0) {
            return true;
        }



        //按value配置
        if (StringUtils.isNotEmpty(permisisonHander.value())) {
            return permissionStr.indexOf(permisisonHander.value())>-1;
        }
        Set<String> permissionSet =new HashSet(Splitter.on(",").omitEmptyStrings().splitToList(permissionStr));

        //是数组的时候
        if (permisisonHander.values().length > 0) {
            //要求全匹配
            if (permisisonHander.needAll()) {
                Set<String> needPermissionSet = Sets.newHashSet(permisisonHander.values());
                return Sets.difference(needPermissionSet, permissionSet).size() == 0;
            } else { //不要求全匹配
                for (String s : permisisonHander.values()) {
                    if (permissionSet.contains(s)) {
                        return true;
                    }
                }
            }
        }
        return false;


    }


    private boolean userRoleAuthValidate(HttpServletRequest request, HasRole roleHander,String roleStr) {
        //如果没写权限要求，那就直接验证通过

        if (StringUtils.isEmpty(roleHander.value()) && roleHander.values().length == 0) {
            return true;
        }


        //按value配置
        if (StringUtils.isNotEmpty(roleHander.value())) {
            return roleStr.indexOf(roleHander.value())>-1;
        }
        Set<String> roles = new HashSet(Splitter.on(",").omitEmptyStrings().splitToList(roleStr));

        //是数组的时候
        if (roleHander.values().length > 0) {
            //要求全匹配
            if (roleHander.needAll()) {
                Set<String> needRoles = Sets.newHashSet(roleHander.values());
                return Sets.difference(needRoles, roles).size() == 0;
            } else { //不要求全匹配
                for (String s : roleHander.values()) {
                    if (roles.contains(s)) {
                        return true;
                    }
                }
            }
        }
        return false;


    }
}
