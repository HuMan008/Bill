package cn.gotoil.bill.web.interceptor.secure;

import cn.gotoil.bill.config.property.BillProperties;
import cn.gotoil.bill.config.property.SecureProperties;
import cn.gotoil.bill.exception.PermissionError;
import cn.gotoil.bill.exception.SecureException;
import cn.gotoil.bill.model.BaseAdminUser;
import cn.gotoil.bill.web.annotation.HasPermision;
import cn.gotoil.bill.web.annotation.NeedLogin;
import cn.gotoil.bill.web.helper.ServletRequestHelper;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;

/**
 * 权限控制拦截器
 *
 * @author SuYajiang SYJ247@qq.com
 * @Date 2018-10-30 11:05
 */
@Component
@SuppressWarnings("all")
public class PermissionInterceptor implements HandlerInterceptor {

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
            throw new SecureException(PermissionError.Need_Login);
        }

        //权限判断
        if (!userAuthValidate(request, handler)) {
            throw new SecureException(PermissionError.PERMISSION_ERROR);
        }
        return true;

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


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {

    }


    private boolean userAuthValidate(HttpServletRequest request, Object handler) {
        HasPermision hander = ((HandlerMethod) handler).getMethodAnnotation(HasPermision.class);
        //如果没写权限要求，那就直接验证通过
        if (hander == null) {
            return true;
        }

        if (StringUtils.isEmpty(hander.value()) && hander.values().length == 0) {
            return true;
        }

        Object o =
                ServletRequestHelper.httpServletRequest().getSession().getAttribute(secureProperties.getSessionKey());
        //如果发现用户未登录
        Optional.ofNullable(o).map(m -> m).orElseThrow(() -> new SecureException(PermissionError.PERMISSION_ERROR));
        BaseAdminUser baseAdminUser = null;
        if (o instanceof BaseAdminUser) {
            baseAdminUser = (BaseAdminUser) o;
        } else {
            throw new SecureException(PermissionError.Need_Login);
        }

        Set<String> permissionSet = baseAdminUser.getPermissions();
        //按value配置
        if (StringUtils.isNotEmpty(hander.value())) {
            return permissionSet.contains(hander.value());
        }

        //是数组的时候
        if (hander.values().length > 0) {
            //要求全匹配
            if (hander.needAll()) {
                Set<String> needPermissionSet = Sets.newHashSet(hander.values());
                return Sets.difference(needPermissionSet, permissionSet).size() == 0;
            } else { //不要求全匹配
                for (String s : hander.values()) {
                    if (permissionSet.contains(s)) {
                        return true;
                    }
                }
            }
        }
        return false;


    }

}
