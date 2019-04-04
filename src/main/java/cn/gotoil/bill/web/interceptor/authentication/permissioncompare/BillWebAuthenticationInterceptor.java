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
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;

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


        //判断session是否存在
        boolean hasLogin = request.getSession().getAttribute(secureProperties.getSessionKey()) != null;


        if (!hasLogin) {
            throw new BillException(CommonError.Need_Login);
        }

        HasPermision permissionHandler = ((HandlerMethod) handler).getMethodAnnotation(HasPermision.class);
        HasRole roleHander = ((HandlerMethod) handler).getMethodAnnotation(HasRole.class);

        boolean vv = true;
        //如果没有写haspermission 也没写hasRole
        if(permissionHandler==null && roleHander ==null){
            return true;
        }
        //hasrole里有货
        if(roleHander!=null){
            vv=userRoleAuthValidate(request,roleHander);
        }
        //hasPermission里有货
        if(permissionHandler!=null){
            vv = vv&& userPermissionAuthValidate(request,permissionHandler);
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

    private boolean userPermissionAuthValidate(HttpServletRequest request, HasPermision permisisonHander) {

        //如果没写权限要求，那就直接验证通过
        if (permisisonHander == null) {
            return true;
        }

        if (StringUtils.isEmpty(permisisonHander.value()) && permisisonHander.values().length == 0) {
            return true;
        }

        Object o =
                request.getSession().getAttribute(secureProperties.getSessionKey());
        //如果发现用户未登录
        Optional.ofNullable(o).map(m -> m).orElseThrow(() -> new BillException(CommonError.PERMISSION_ERROR));
        BaseAdminUser baseAdminUser = null;
        if (o instanceof BaseAdminUser) {
            baseAdminUser = (BaseAdminUser) o;
        } else {
            throw new BillException(CommonError.Need_Login);
        }

        Set<String> permissionSet = baseAdminUser.getPermissions();
        //按value配置
        if (StringUtils.isNotEmpty(permisisonHander.value())) {
            return permissionSet.contains(permisisonHander.value());
        }

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


    private boolean userRoleAuthValidate(HttpServletRequest request, HasRole roleHander) {
        //如果没写权限要求，那就直接验证通过
        if (roleHander == null) {
            return true;
        }

        if (StringUtils.isEmpty(roleHander.value()) && roleHander.values().length == 0) {
            return true;
        }

        Object o =
               request.getSession().getAttribute(secureProperties.getSessionKey());
        //如果发现用户未登录
        Optional.ofNullable(o).map(m -> m).orElseThrow(() -> new BillException(CommonError.PERMISSION_ERROR));
        BaseAdminUser baseAdminUser = null;
        if (o instanceof BaseAdminUser) {
            baseAdminUser = (BaseAdminUser) o;
        } else {
            throw new BillException(CommonError.Need_Login);
        }

        Set<String> roles = baseAdminUser.getRoles();

        //按value配置
        if (StringUtils.isNotEmpty(roleHander.value())) {
            return roles.contains(roleHander.value());
        }

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
