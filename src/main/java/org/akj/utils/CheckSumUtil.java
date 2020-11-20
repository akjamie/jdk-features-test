package org.akj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class CheckSumUtil {
    private static final int READ_BUFFER = 1024;

    public static String getCheckSum(MessageDigest digest, File file) throws IOException {
        //1. read file data and update in message digest
        try (FileInputStream ios = new FileInputStream(file);) {
            byte[] bytes = new byte[READ_BUFFER];

            int len = 0;
            while((len = ios.read()) != -1){
                digest.update(bytes,0,1);
            }

        }

        //2.generate hash's bytes
        byte[] hashBytes = digest.digest();

        //3.convert to hexadecimal format
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < hashBytes.length; i++){
            builder.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return builder.toString();
    }
}
