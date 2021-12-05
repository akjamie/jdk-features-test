package org.akj.jdk8.features.annotation;

import java.lang.annotation.Repeatable;

@Repeatable(value = Tenants.class)
public @interface Tenant{
    String value();
}