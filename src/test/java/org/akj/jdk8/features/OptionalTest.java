package org.akj.jdk8.features;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@Slf4j
public class OptionalTest {
    /*
     * two difference here:
     * 1. orElseGet must consume a supplier functional interface
     * 2. orElseGet won't execute the function when wrapped value is present, but orElse always execute
     * if function placed there as default value, so orElseGet may have better performance especially webservice or
     * api call happens in the supplier function.method
     */

    @Test
    public void test() {
        String msg = "this is for testing";

        log.debug("using the orElseGet");
        String actualMsg = Optional.ofNullable(msg).orElseGet(() -> "this is default");
        log.debug("actual message: {}", actualMsg);

        log.debug("using the orElse");
        actualMsg = Optional.ofNullable(msg).orElse("this is default");
        log.debug("actual message: {}", actualMsg);
    }

    @Test
    public void test1() {
        String msg = "this is for testing";

        log.debug("using the orElseGet");
        String actualMsg = Optional.ofNullable(msg).orElseGet(this::getDefaultString);
        log.debug("actual message: {}", actualMsg);

        log.debug("using the orElse");
        actualMsg = Optional.ofNullable(msg).orElse(getDefaultString());
        log.debug("actual message: {}", actualMsg);
    }

    private String getDefaultString() {
        log.debug("exec getDefaultString method to get the default message.");
        return "this is default message";
    }

}
