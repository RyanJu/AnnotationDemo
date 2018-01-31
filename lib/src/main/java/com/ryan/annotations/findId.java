package com.ryan.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2018/1/29 15:51 1.0
 * @time 2018/1/29 15:51
 * @project AnnotationDemo com.ryan.annotations
 * @description
 * @updateVersion 1.0
 * @updateTime 2018/1/29 15:51
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface findId {
    int value();
}
