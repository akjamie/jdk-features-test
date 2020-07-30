package org.akj.multithread.unsafe;

import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeBasicTest {

    @Test
    public void test() throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        // case1.bypass the class constructor
        Unsafe unsafe = UnsafeUtils.getUnsafe();
        Class.forName("org.akj.multithread.unsafe.UnsafeBasicTest$Simple");

//        Simple instance = (Simple) unsafe.allocateInstance(Simple.class);
//        System.out.printf("[index]: %d \n", instance.getIndex());
//        System.out.println(instance.getClass());
//        System.out.println(instance.getClass().getClassLoader());

        Simple obj = new Simple();
        obj.test();
        Field f = obj.getClass().getDeclaredField("index");
        unsafe.putInt(obj, unsafe.objectFieldOffset(f), 1000);
        obj.test();
        System.out.printf("[index]: %d", obj.getIndex());
    }

    //    @Data
    class Simple {
        private int index = 999;

        public Simple() {
            this.index = 1;

            System.out.printf("initializing [index]: %d \n", index);
        }

        public int getIndex() {
            return index;
        }

        public void test(){
            if(index == 1000){
                System.out.println("execute data clean up...");
            }
        }
    }
}
