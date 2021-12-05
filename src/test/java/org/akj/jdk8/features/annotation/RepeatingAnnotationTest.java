package org.akj.jdk8.features.annotation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@Slf4j
public class RepeatingAnnotationTest {
    @Test
    public void testWithVersionOfJdk8Plus() {
        Tenants tenants = SampleService.class.getAnnotation(Tenants.class);
        Arrays.asList(tenants.value()).stream().forEach(i -> {
            log.debug(i.value());
        });
    }

    @Test
    public void testWithTraditionalApproach(){
        TraditionalTenants traditionalTenants = TraditionalSampleService.class.getAnnotation(TraditionalTenants.class);
        Arrays.asList(traditionalTenants.value()).stream().forEach(i -> {
            log.debug(i.value());
        });
    }
}
