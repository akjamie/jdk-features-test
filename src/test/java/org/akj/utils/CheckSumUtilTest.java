package org.akj.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class CheckSumUtilTest {


    @Test
    public void testGetCheckSum() throws NoSuchAlgorithmException, IOException, URISyntaxException {
        File file = new File(CheckSumUtilTest.class.getClassLoader().getSystemResource("jdbc.properties").toURI());

        //Use MD5 algorithm
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");

        //Get the checksum
        String checksum = CheckSumUtil.getCheckSum(md5Digest, file);
        System.out.println(checksum);
    }

}