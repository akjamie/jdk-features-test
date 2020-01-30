package org.akj.multithread.unsafe;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

@Slf4j
public final class UnsafeUtils {
    public static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = null;
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Exception e) {
            log.error("got exception when try to get unsafe instance,{}", e);
        }

        return unsafe;
    }

}
