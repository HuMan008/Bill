/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.aspect.DolphinApiResponseAspect
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 1/4/17 10:14 AM
 *
 */

package cn.gotoil.bill.aspect;


import cn.gotoil.bill.exception.CommonError;

import cn.gotoil.bill.web.message.BillApiResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

//@Aspect
//@Component
public class ApiResponseAspect {

    //    private static Logger logger = LoggerFactory.getLogger(ApiResponseAspect.class);
    //
    //
    //    @Autowired
    //    private BillProperties billProperties;
    //
    //    @Autowired
    //    private DefaultListableBeanFactory beanFactory;
    //
    //    @PostConstruct
    //    public void construct() {
    //
    //        if (billProperties == null) {
    //            return;
    //        }
    //
    //        String execution = billProperties.getApiResponseAroundControllerPackagesExecution();
    //        if (StringUtils.isEmpty(execution)) {
    //            return;
    //        }
    //
    //
    //        logger.info("ApiResponseAroundControllerPackagesExecution:{}", execution);
    //        try {
    //            Method method = ApiResponseAspect.class.getMethod("around", ProceedingJoinPoint.class);
    //            Around around = method.getAnnotation(Around.class);
    //            ObjectHelper.changeAnnotationValue(around, "value", execution);
    //            if (around != null) {
    //                logger.info("{}", around);
    //            }
    //        } catch (Exception ex) {
    //            logger.error("ApiResponseAspect:{}", ex);
    //        }
    //    }



    private static String formatBindingErrorMessages(BindingResult bindingResult) {
        /*
        return bindingResult.getAllErrors()
                .stream().map(err -> {
                    if (err instanceof FieldError && !err.getDefaultMessage().matches("<\\w*>")) {
                        return String.format("<%s> %s", ((FieldError) err).getField(), err.getDefaultMessage());
                    }
                    return err.getDefaultMessage();
                }).reduce("", (p, c) -> p + c + ", ");

        */

        StringBuilder builder = new StringBuilder(128);
        for (ObjectError error : bindingResult.getAllErrors()) {
            if (error instanceof FieldError && !error.getDefaultMessage().matches("<\\w*>")) {
                builder.append('<');
                builder.append(((FieldError) error).getField());
                builder.append("> ");
            }
            builder.append(error.getDefaultMessage());
            builder.append(',');
        }

        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }

    //    @Around("execution(* cn.gotoil.bill.web.controller.v1..*.*Action(..))")
    //    public Object around(ProceedingJoinPoint point) throws Throwable {
    //        return aroundCall(point);
    //    }

    protected Object aroundCall(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()) {
                    BillApiResponse response = new BillApiResponse();
                    response.setStatus(CommonError.ValidateError.getCode());
                    response.setMessage(formatBindingErrorMessages(bindingResult));
                    return response;
                }
            }
        }

        Object actionObject = point.proceed();

        if (actionObject instanceof BillApiResponse) {
            return actionObject;
        } else {
            return new BillApiResponse(actionObject);
        }
    }

}


