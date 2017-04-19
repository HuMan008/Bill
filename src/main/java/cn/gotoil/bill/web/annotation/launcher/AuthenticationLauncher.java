/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.annotation.launcher.AuthenticationLauncher
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 1/4/17 10:14 AM
 *
 */

package cn.gotoil.bill.web.annotation.launcher;


import cn.gotoil.bill.config.property.BillProperties;
import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;


@Component
public class AuthenticationLauncher {

    @Autowired
    private BillProperties billProperties;


    public AuthenticationType getAuthenticationType(Object object) {
        AuthenticationType billApiAuthenticationType = billProperties.getDefaultAuthenticationType();
        if (object instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) object;
            Authentication authenticationAnnotation = handlerMethod.getMethod().getAnnotation(Authentication.class);
            if (null == authenticationAnnotation) {
                authenticationAnnotation = handlerMethod.getBeanType().getAnnotation(Authentication.class);
            }

            if (authenticationAnnotation != null) {
                billApiAuthenticationType = authenticationAnnotation.authenticationType();
            }
        }

        return billApiAuthenticationType;
    }
}
