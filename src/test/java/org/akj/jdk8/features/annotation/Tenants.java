package org.akj.jdk8.features.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Tenants{
    Tenant[] value() default {};
}