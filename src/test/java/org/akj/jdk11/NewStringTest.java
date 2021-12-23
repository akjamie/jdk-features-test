package org.akj.jdk11;

import org.junit.jupiter.api.Test;

public class NewStringTest {
    @Test
    public void test(){
        String testMsg = "  this is testing message, please do not reply, thanks  ";

        System.out.println(testMsg.trim());
        System.out.println(testMsg.strip());
        System.out.println(testMsg.isEmpty());
    }
}
