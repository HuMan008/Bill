/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.annotation.launcher.ThridValidationLauncher
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package cn.gotoil.bill.web.annotation.launcher;


import cn.gotoil.bill.web.annotation.ThirdValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class ThridValidationLauncher implements ConstraintValidator<ThirdValidation, Object> {

    private static Logger logger = LoggerFactory.getLogger(ThridValidationLauncher.class);

    private Class clazz = null;

    private Method method = null;

    private String methodName = null;

    private Pattern regex = null;

    private boolean staticMethod = false;

    @Override
    public void initialize(ThirdValidation constraintAnnotation) {
        staticMethod = constraintAnnotation.staticMethod();

        if (constraintAnnotation.regex().length() > 0) {
            try {
                regex = Pattern.compile(constraintAnnotation.regex());
            } catch (Exception ex) {
                logger.error("{}", ex);
            }

            return;
        }

        try {
            clazz = Class.forName(constraintAnnotation.className());
        } catch (ClassNotFoundException ex) {
            logger.error("{}", ex);
            clazz = null;
        }

        methodName = constraintAnnotation.methodName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        if (regex != null) {
            return regex.matcher(value.toString()).matches();
        }


        if (clazz == null) {
            return false;
        }

        try {
            method = clazz.getMethod(methodName, value.getClass());
        } catch (NoSuchMethodException ex) {
            logger.error("{}", ex);
            return false;
        }

        try {
            Boolean ret;
            if (staticMethod) {
                ret = (Boolean) method.invoke(null, value);
            } else {
                ret = (Boolean) method.invoke(clazz, value);
            }
            return ret;
        } catch (InvocationTargetException ex) {
            logger.error("{}", ex.getTargetException());
            return false;
        } catch (IllegalAccessException ex) {
            logger.error("{}", ex);
            return false;
        }
    }

}
