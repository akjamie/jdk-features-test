package org.akj.multithread.atomic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class AtomicBooleanTest {
    @Test
    public void test(){
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        assertFalse(atomicBoolean.get());

        // update failed, assertion should pass
        boolean newBoolean = atomicBoolean.compareAndSet(true, false);
        assertFalse(atomicBoolean.get());

        //
        boolean result = atomicBoolean.getAndSet(true);
        assertFalse(result);
        assertTrue(atomicBoolean.get());
        System.out.println(atomicBoolean.getOpaque());
        System.out.println(atomicBoolean.getPlain());
        System.out.println(atomicBoolean.getOpaque());

    }
}
