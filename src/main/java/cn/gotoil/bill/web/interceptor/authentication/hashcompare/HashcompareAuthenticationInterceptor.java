/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.interceptor.AuthenticationInterceptor
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package cn.gotoil.bill.web.interceptor.authentication.hashcompare;


import cn.gotoil.bill.config.property.BillProperties;
import cn.gotoil.bill.exception.BillException;
import cn.gotoil.bill.exception.CommonError;
import cn.gotoil.bill.web.annotation.launcher.AuthenticationLauncher;
import cn.gotoil.bill.web.filter.BodyContentHttpServletRequestWrapper;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


@Component
public class HashcompareAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private BillProperties billProperties;

    @Autowired
    private AuthenticationLauncher authenticationLauncher;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Method authKeyMethod = null;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String uri = request.getRequestURI();

        /*
        boolean matchRequireAuth = false;
        for (String prefix : billProperties.getRequireAuthUrlPrefixs()) {
            if (uri.startsWith(prefix)) {
                matchRequireAuth = true;
                continue;
            }
        }

        if (!matchRequireAuth) {
            return true;
        }
        */

        if (!(request instanceof BodyContentHttpServletRequestWrapper)) {
            if (!billProperties.getExceptBodyContentHttpServletRequestWrapperUrls().contains(uri)) {
                throw new BillException(CommonError.Unsupported);
            }
        }

        AuthenticationType billApiAuthenticationType = authenticationLauncher.getAuthenticationType(handler);
        if (AuthenticationType.None == billApiAuthenticationType) {
            return true;
        }

        if (authKeyMethod == null) {
            synchronized (this) {
                if (authKeyMethod == null) {
                    authKeyMethod = Class.forName(billProperties.getKeyOfHashCompareAuthenticationProviderClass()).
                            getMethod(billProperties.getKeyOfHashCompareAuthenticationProviderMethod(), String.class);
                }
            }
        }

        String body = null;
        if (request instanceof BodyContentHttpServletRequestWrapper) {
            BodyContentHttpServletRequestWrapper wrapper = (BodyContentHttpServletRequestWrapper) request;
            body = wrapper.getHttpBody();
        }

        HashcompareAuthenticationInterceptorSignatureVerifier verifier =
                new HashcompareAuthenticationInterceptorSignatureVerifier(request, body);
        verifier.setStringRedisTemplate(stringRedisTemplate);
        verifier.setAuthKeyMethod(authKeyMethod);
        verifier.verify(billApiAuthenticationType);

        return true;
    }


}


