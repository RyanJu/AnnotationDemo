package com.ryan.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2018/1/31 9:17 1.0
 * @time 2018/1/31 9:17
 * @project AnnotationDemo com.ryan.annotations
 * @description
 * @updateVersion 1.0
 * @updateTime 2018/1/31 9:17
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface click {
    int[] value() default {0};
}
