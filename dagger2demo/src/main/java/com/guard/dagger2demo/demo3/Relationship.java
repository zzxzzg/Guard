package com.guard.dagger2demo.demo3;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by yxwang on 16-4-14.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Relationship {
    String value() default "";
}
