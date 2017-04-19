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


import cn.gotoil.bill.exception.AuthenticationDebuger;
import cn.gotoil.bill.exception.BillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AllExceptionHandler implements HandlerExceptionResolver, Ordered {

    private static Logger logger = LoggerFactory.getLogger(AllExceptionHandler.class);


    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        logger.error("RestExceptionHandler.resolveException:{}", ex);

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
