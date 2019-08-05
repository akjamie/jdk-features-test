package org.akj.instrument;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class CustomClassFileTransformer implements ClassFileTransformer {

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		System.out.println("class loader: " + loader.getClass());
		System.out.println("class name: " + className);
		// return ClassFileTransformer.super.transform(loader, className,
		// classBeingRedefined, protectionDomain,
		// classfileBuffer);

		return null;
	}
}
