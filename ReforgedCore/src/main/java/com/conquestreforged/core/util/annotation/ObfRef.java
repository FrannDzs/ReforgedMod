package com.conquestreforged.core.util.annotation;

public @interface ObfRef {

    Class<?> value();

    String field() default "";

    String method() default "";
}
