package org.akj.multithread.pattern.readwritelock;

public class SharedData {
    private final char[] token;

    private final static int DEFAULT_TOKEN_LENGTH = 64;

    private int tokenLength;

    public SharedData() {
        this(DEFAULT_TOKEN_LENGTH);
    }

    private ReadWriteLock lock = new ReadWriteLock(true);

    public SharedData(int tokenLength) {
        this.tokenLength = tokenLength;
        this.token = new char[tokenLength];
    }

    public int getTokenLength() {
        return this.tokenLength;
    }

    public char[] read() throws InterruptedException {
        try {
            lock.readLock();
            Thread.sleep(500);
            char[] copy = new char[token.length];
            for (int i = 0; i < token.length; i++) copy[i] = token[i];

            return copy;
        } finally {
            lock.readUnlock();
        }
    }

    public void write(char[] token) throws InterruptedException {
        try {
            lock.writeLock();
            Thread.sleep(1000);
            // FIXME the this.token.length != token.length
            for (int i = 0; i < token.length; i++) {
                this.token[i] = token[i];
            }
        } finally {
            lock.writeUnlock();
        }
    }
}
