package org.akj.jdk9.features;

public interface Processor {
    default boolean process() {
        //do nothing
        updateStatus();
        return true;
    }

    private void updateStatus() {
        //do nothing
    }
}
