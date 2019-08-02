package org.akj.jdk8.features.classloader;

import lombok.Data;

import java.io.*;

@Data
public class MyClassLoader  extends ClassLoader {
    private String classPath;
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] loadClassData(String name) {
        String classFileName = classPath + File.separatorChar
                + name.replace('.', File.separatorChar) + ".class";

        try {
            InputStream ins = new FileInputStream(classFileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            while ((length = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
