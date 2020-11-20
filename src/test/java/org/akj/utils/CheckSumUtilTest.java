package org.akj.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
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

    @Test
    public void testGetCheckSum_SHA256() throws NoSuchAlgorithmException, IOException, URISyntaxException {
        File file = new File(CheckSumUtilTest.class.getClassLoader().getSystemResource("jdbc.properties").toURI());

        //Use MD5 algorithm
        MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");

        //Get the checksum
        String checksum = CheckSumUtil.getCheckSum(shaDigest, file);
        System.out.println(checksum);
    }

    @Test
    public void testGetCheckSum_SHA512() throws NoSuchAlgorithmException, IOException, URISyntaxException {
        File file = new File(CheckSumUtilTest.class.getClassLoader().getSystemResource("jdbc.properties").toURI());

        //Use MD5 algorithm
        MessageDigest shaDigest = MessageDigest.getInstance("SHA-512");

        //Get the checksum
        String checksum = CheckSumUtil.getCheckSum(shaDigest, file);
        System.out.println(checksum);
    }

    @Test
    public void testGetCheckSum_SHA512_verify() throws NoSuchAlgorithmException, IOException, URISyntaxException {
        // file1 is copy of file, file2 is copy of file and made changes
        File file = new File(CheckSumUtilTest.class.getClassLoader().getSystemResource("jdbc.properties").toURI());
        File file1 = new File(CheckSumUtilTest.class.getClassLoader().getSystemResource("jdbc1.properties").toURI());
        File file2 = new File(CheckSumUtilTest.class.getClassLoader().getSystemResource("jdbc2.properties").toURI());

        //Use MD5 algorithm
        MessageDigest shaDigest = MessageDigest.getInstance("SHA-512");
        //Get the checksum
        String checksum = CheckSumUtil.getCheckSum(shaDigest, file);
        String checksum1 = CheckSumUtil.getCheckSum(shaDigest, file1);
        Assertions.assertEquals(checksum,checksum1);

        // they are not same as file content change
        String checksum2 = CheckSumUtil.getCheckSum(shaDigest, file2);
        Assertions.assertNotEquals(checksum,checksum2);
    }

    @Test
    public void testGetCheckSum_SHA512_verify_newlib() throws NoSuchAlgorithmException, IOException, URISyntaxException {
        // file1 is copy of file, file2 is copy of file and made changes
        File file = new File(CheckSumUtilTest.class.getClassLoader().getSystemResource("jdbc.properties").toURI());

        //Use MD5 algorithm
        MessageDigest shaDigest = MessageDigest.getInstance("SHA-512");
        //Get the checksum
        String checksum = CheckSumUtil.getCheckSum(shaDigest, file);

        //use common utils in apache commons-codec
        String s = DigestUtils.sha512Hex(new FileInputStream(file));

        Assertions.assertEquals(s,checksum);
    }

}