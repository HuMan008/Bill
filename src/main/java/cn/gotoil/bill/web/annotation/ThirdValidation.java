/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.annotation.ThirdValidation
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 1/4/17 10:14 AM
 *
 */

package cn.gotoil.bill.web.annotation;


import cn.gotoil.bill.web.annotation.launcher.ThridValidationLauncher;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ThridValidationLauncher.class)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ThirdValidation {
    String message() default "Validation Faild.";

    String className() default "";

    String methodName() default "";

    boolean staticMethod() default false;

    String regex() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
