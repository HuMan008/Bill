/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.exception.handler.AllExceptionHandler
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package cn.gotoil.bill.exception.handler;


import cn.gotoil.bill.config.property.SecureProperties;
import cn.gotoil.bill.exception.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("unused")
@Component
public class AllExceptionHandler implements HandlerExceptionResolver, Ordered {

    private static Logger logger = LoggerFactory.getLogger(AllExceptionHandler.class);
    @Autowired
    SecureProperties secureProperties;

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        logger.error("RestExceptionHandler.resolveException:{}", ex);

        if(ex instanceof BusinessException){
            String requestType = request.getHeader("X-Requested-With");
            if (StringUtils.isNotEmpty(requestType) && "XMLHttpRequest".equalsIgnoreCase(requestType)) {
                MappingJackson2JsonView view = new MappingJackson2JsonView();
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setView(view);
                BillException dolphinException = (BillException) ex;
                modelAndView.addObject("status", dolphinException.getTickcode());
                modelAndView.addObject("message", dolphinException.getMessage());
                return modelAndView;

            } else {
                ModelAndView modelAndView = new ModelAndView(secureProperties.getErrorPageUrl());
                return modelAndView;
            }

        }

        if (ex instanceof BillException) {
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setView(view);
            BillException dolphinException = (BillException) ex;
            modelAndView.addObject("status", dolphinException.getTickcode());
            modelAndView.addObject("message", dolphinException.getMessage());

            Object debuger = request.getAttribute(AuthenticationDebuger.AuthenticationDebugerKey);
            if (debuger != null) {
                modelAndView.addObject("debug", debuger);
            }

            return modelAndView;
        }


        if (ex instanceof SecureException) {
            String requestType = request.getHeader("X-Requested-With");
            if (StringUtils.isNotEmpty(requestType) && "XMLHttpRequest".equalsIgnoreCase(requestType)) {
                try {
                    response.getWriter().print("<script language=\"javascript\">alert('" + ex.getMessage() + "');" +
                            "</script>");
                } catch (IOException e1) {
                    logger.error("{}", e1);
                }
                return null;

            } else {
                ModelAndView modelAndView = new ModelAndView(secureProperties.getLoginPageUrl());
                return modelAndView;
            }
        }

        if (ex instanceof NoHandlerFoundException) {
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setView(view);
            modelAndView.addObject("status", 404);
            modelAndView.addObject("message", ex.getMessage());
            return modelAndView;
        }

        MappingJackson2JsonView view = new MappingJackson2JsonView();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(view);
        modelAndView.addObject("status", 500);
        modelAndView.addObject("message", ex.getMessage());

        return modelAndView;
    }


}
