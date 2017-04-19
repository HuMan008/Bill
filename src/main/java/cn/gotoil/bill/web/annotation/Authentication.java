/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.annotation.Authentication
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 1/4/17 10:14 AM
 *
 */

package cn.gotoil.bill.web.annotation;


import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authentication {

    AuthenticationType authenticationType() default AuthenticationType.None;
}
