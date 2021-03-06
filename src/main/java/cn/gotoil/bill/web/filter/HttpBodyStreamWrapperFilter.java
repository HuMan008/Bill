/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.filter.HttpBodyStreamWrapperFilter
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package cn.gotoil.bill.web.filter;


import cn.gotoil.bill.config.property.BillProperties;
import cn.gotoil.bill.web.interceptor.authentication.BillHashCompareAuthHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@SuppressWarnings("unused")
@Component
//@WebFilter(filterName = "HttpBodyStreamWrapperFilter", urlPatterns = "/api/*")
public class HttpBodyStreamWrapperFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(HttpBodyStreamWrapperFilter.class);

    @Autowired
    private BillProperties billProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest)) {
            logger.error("Request not instanceof HttpServletRequest");
        }

        String url = ((HttpServletRequest) request).getRequestURI();

        if (BillHashCompareAuthHelper.isSkipBillHashURL(url, billProperties)) {
            chain.doFilter(request, response);
            return;
        }


        BodyContentHttpServletRequestWrapper bodyContentHttpServletRequestWrapper = new BodyContentHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(bodyContentHttpServletRequestWrapper, response);
    }



    @Override
    public void destroy() {

    }
}

