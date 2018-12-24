package cn.gotoil.bill.web.annotation;

import java.lang.annotation.*;

/**
 * 是否需要登录的
 * Created by think on 2017/11/21.
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedLogin {
    boolean value() default true;
}
