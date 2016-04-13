package com.guard.dagger2demo.demo1;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by yxwang on 16-4-13.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentScope {
}
