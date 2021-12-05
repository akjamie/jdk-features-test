package org.akj.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ChannelTest {
    @Test
    public void test() throws IOException {
        FileChannel fileChannel = FileChannel.open(Paths.get("README.md"), StandardOpenOption.READ);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while(fileChannel.read(byteBuffer) != -1){
//            byteBuffer.flip();
            System.out.println(byteBuffer);
            byteBuffer.clear();
        }
        fileChannel.close();
    }
}
