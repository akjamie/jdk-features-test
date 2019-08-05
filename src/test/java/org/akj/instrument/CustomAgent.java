package org.akj.instrument;

import java.lang.instrument.Instrumentation;

public class CustomAgent {

	/**
	 * register the transfer
	 * 
	 * @param args            jarpath
	 * @param instrumentation jvm built-in object, use for ClassFileTransformer
	 *                        registration
	 */
	public static void premain(String args, Instrumentation instrumentation) {
		CustomClassFileTransformer transformer = new CustomClassFileTransformer();

		instrumentation.addTransformer(transformer);
	}
}
