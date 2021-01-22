package com.mall.ninecommunity.aspect;

import androidx.annotation.Keep;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Keep
public @interface PermissionTrace {
    String[] permissions();

    boolean force() default true;
}
