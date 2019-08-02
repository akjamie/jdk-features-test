package org.akj.jdk9.features;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class TryWithResourcesTest {

    @SuppressWarnings("static-access")
    @Test
    final void test() throws IOException {
        InputStream ins = this.getClass().getClassLoader().getSystemResourceAsStream("input.txt");
        BufferedReader bufferedIns = new BufferedReader(new InputStreamReader(ins));
        try (bufferedIns) {
//            char[] cbuf = new char[1024];
////            while (bufferedIns.read(cbuf) != -1) {
////                System.out.println(cbuf);
////            }
            String s = new String();
            while ((s = bufferedIns.readLine()) != null) {
                System.out.println(s);
            }
        }
    }

}
