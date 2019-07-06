package org.akj.jdk9.features;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

class TryWithResourcesTest {

	@SuppressWarnings("static-access")
	@Test
	final void test() throws IOException {
		
		InputStream ins = this.getClass().getClassLoader().getSystemResourceAsStream("input.txt");
		BufferedReader bufferedIns = new BufferedReader(new InputStreamReader(ins));
		try(bufferedIns){
			char[] cbuf = new char[1024];
			while(bufferedIns.read(cbuf) != -1) {
				System.out.println(cbuf);
			}
		}
	}

}
