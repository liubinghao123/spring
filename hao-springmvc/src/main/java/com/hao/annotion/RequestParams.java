package com.hao.annotion;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParams {
    String value() default "";
    boolean required() default false;
}
