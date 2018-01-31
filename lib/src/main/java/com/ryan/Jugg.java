package com.ryan;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2018/1/30 16:04 1.0
 * @time 2018/1/30 16:04
 * @project AnnotationDemo com.ryan
 * @description
 * @updateVersion 1.0
 * @updateTime 2018/1/30 16:04
 */

public class Jugg {
    public static void bind(Object target) {
        String binderClassName = target.getClass().getName() + "__ViewFinder";
        try {
            Class clz = Class.forName(binderClassName);
            Object object = clz.newInstance();
            Method findView = clz.getMethod("inject", target.getClass());
            findView.invoke(object,target);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
