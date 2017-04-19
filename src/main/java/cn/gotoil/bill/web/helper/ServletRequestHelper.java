/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.helper.ServletRequestHelper
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package cn.gotoil.bill.web.helper;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ServletRequestHelper {

    public static String XU() {
        HttpServletRequest request = httpServletRequest();
        if (request == null) {
            return null;
        }
        return request.getHeader("X-U");
    }

    public static String userAgent() {
        HttpServletRequest request = httpServletRequest();
        if (request == null) {
            return null;
        }
        
        return request.getHeader("User-Agent");
    }


    public static HttpServletRequest httpServletRequest() {
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return servletRequestAttributes.getRequest();
        } catch (Exception ex) {
            return null;
        }

    }
}
