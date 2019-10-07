package org.akj.multithread.pattern.observer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OctObserver extends Observer {
    public OctObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update(Object args) {
        log.debug("the state of subject is changed, the new status is {}", Integer.toOctalString(subject.getState()));
    }
}
