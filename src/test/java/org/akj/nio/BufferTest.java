package org.akj.nio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BufferTest {

    @Test
    public void test() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println(String.format("initialized - capacity: %d, limit: %d, position: %d, mark: %s", byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.mark()));

        byteBuffer.put("test message".getBytes(StandardCharsets.UTF_8));
        System.out.println(String.format("after put data - capacity: %d, limit: %d, position: %d, mark: %s", byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.mark()));

        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println(new String(bytes));
        System.out.println(String.format("flip and read all data - capacity: %d, limit: %d, position: %d, mark: %s", byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.mark()));

        byteBuffer.reset();
        System.out.println(String.format("reset - capacity: %d, limit: %d, position: %d, mark: %s", byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.mark()));

        byteBuffer.clear();
        System.out.println(String.format("clear - capacity: %d, limit: %d, position: %d, mark: %s", byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position(), byteBuffer.mark()));

        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            byteBuffer.limit(1025);
        });

    }
}
