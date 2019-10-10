package org.akj.multithread.atomic;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicIntegerFieldUpdaterTest {

    @Test
    public void testAtomicIntegerFieldUpdater() {
        AtomicIntegerFieldUpdater<TargetObject> fieldUpdater =
                AtomicIntegerFieldUpdater.newUpdater(TargetObject.class, "counter");

        TargetObject object = new TargetObject();
        int andIncrement = fieldUpdater.getAndIncrement(object);
        Assertions.assertEquals(0, andIncrement);
    }

    @Test
    public void testAtomicReferenceFieldUpdater() {
        AtomicReferenceFieldUpdater<TargetObject, Integer> fieldUpdater =
                AtomicReferenceFieldUpdater.newUpdater(TargetObject.class, Integer.class,
                        "index");

        TargetObject object = new TargetObject();
        object.setIndex(0);
        fieldUpdater.compareAndSet(object, 0, 10);
        Assertions.assertEquals(10, object.getIndex().intValue());
    }

    @Test
    public void testAtomicReferenceFieldUpdaterWhenTargetObjectIsNull() {
        Assertions.assertThrows(ClassCastException.class, () -> {
            AtomicReferenceFieldUpdater<TargetObject, Integer> fieldUpdater =
                    AtomicReferenceFieldUpdater.newUpdater(TargetObject.class, Integer.class,
                            "index");

            fieldUpdater.compareAndSet(null, 0, 10);
        });
    }

    @Test
    public void testAtomicReferenceFieldUpdaterWhenTargetFieldIsPrivate() {
        Assertions.assertThrows(RuntimeException.class, () -> {

            AtomicReferenceFieldUpdater<TargetObject, Integer> fieldUpdater =
                    AtomicReferenceFieldUpdater.newUpdater(TargetObject.class, Integer.class,
                            "i");

            TargetObject object = new TargetObject();
            object.setIndex(0);
            fieldUpdater.compareAndSet(object, 0, 10);
            Assertions.assertEquals(10, object.getIndex().intValue());
        });
    }

    @Test
    public void testAtomicReferenceFieldUpdaterWhenTargetFieldIsProtected() {
        Assertions.assertThrows(RuntimeException.class, () -> {

            AtomicReferenceFieldUpdater<TargetObject, Integer> fieldUpdater =
                    AtomicReferenceFieldUpdater.newUpdater(TargetObject.class, Integer.class,
                            "j");

            TargetObject object = new TargetObject();
            object.setIndex(0);
            fieldUpdater.compareAndSet(object, 0, 10);
            Assertions.assertEquals(10, object.getIndex().intValue());
        });
    }

    @Test
    public void testAtomicReferenceFieldUpdaterOnNonVolatileField() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {

            AtomicReferenceFieldUpdater<TargetObject, Integer> fieldUpdater =
                    AtomicReferenceFieldUpdater.newUpdater(TargetObject.class, Integer.class,
                            "k");

            TargetObject object = new TargetObject();
            object.setIndex(0);
            fieldUpdater.compareAndSet(object, 0, 10);
            Assertions.assertEquals(10, object.getIndex().intValue());
        });
    }

    @Data
    class TargetObject {
        volatile int counter;

        volatile Integer index;

        private Integer i;

        protected Integer j;

        Integer k;
    }
}
