package org.akj.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Slf4j
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

    @Test
    public void checkSumforLargeFile() throws IOException, NoSuchAlgorithmException {
        long currrent = System.currentTimeMillis();
        // file size around 2.4GB
        File file = new File("/Users/jamie/test-01.mkv");
        //Get the checksum
        String s = DigestUtils.sha512Hex(new FileInputStream(file));
//        String s = DigestUtils.sha256Hex(new FileInputStream(file));
//        String s = DigestUtils.sha1Hex(new FileInputStream(file));

        log.info(s);
        log.info("file size: {} mb, time wasted: {} seconds", Optional.ofNullable(file.length()).orElse(0l)/1024/1024,((System.currentTimeMillis() - currrent) / 1000));
    }

}