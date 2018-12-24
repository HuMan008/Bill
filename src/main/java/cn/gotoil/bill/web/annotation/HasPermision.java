package cn.gotoil.bill.web.annotation;

import java.lang.annotation.*;

/**
 * Created by think on 2017/9/14.
 * value 单单匹配是否具备某个权限
 * values  当needAll为ture时，登录用户必须具备values里面的所有值。
 * 当needAll为false时，登录用于只需要具备values里的任意值
 */


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Documented
public @interface HasPermision {

    //单个条件用这个
    String value() default "";

    //多个条件用这个
    String[] values() default {};

    /**
     * 是不是必须要所有权限都满足
     */
    boolean needAll() default false;
}
