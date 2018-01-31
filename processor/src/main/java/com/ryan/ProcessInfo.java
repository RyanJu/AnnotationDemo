package com.ryan;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.lang.String;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2018/1/30 14:30 1.0
 * @time 2018/1/30 14:30
 * @project AnnotationDemo com.ryan
 * @description
 * @updateVersion 1.0
 * @updateTime 2018/1/30 14:30
 */

public class ProcessInfo {
    TypeSpec typeSpec;
    MethodSpec findViewSpec;
    String keyClassName;
    String packageName;

    public MethodSpec getFindViewSpec() {
        return findViewSpec;
    }

    public void setFindViewSpec(MethodSpec findViewSpec) {
        this.findViewSpec = findViewSpec;
    }

    public TypeSpec getTypeSpec() {
        return typeSpec;
    }

    public void setTypeSpec(TypeSpec typeSpec) {
        this.typeSpec = typeSpec;
    }

    public String getKeyClassName() {
        return keyClassName;
    }

    public void setKeyClassName(String keyClassName) {
        this.keyClassName = keyClassName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
