package org.akj.multithread.pattern.observer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HexObserver extends Observer {
    public HexObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update(Object args) {
        log.debug("subject state is changed, the new state is {}", Integer.toHexString(this.subject.getState()).toUpperCase());
    }
}
