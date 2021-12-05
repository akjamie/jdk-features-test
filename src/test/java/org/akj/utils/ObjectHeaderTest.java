package org.akj.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class ObjectHeaderTest {

    @Test
    public void test() throws NoSuchAlgorithmException {
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        log.info(ClassLayout.parseInstance(md5Digest).toPrintable());
    }
}
