package org.akj.snowflake;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.akj.utils.IpUtils;

/* Distributed id generation service using customized Snowflake
 * 64 bits = 1 bit + 41 bits timestamp + 10 bits ipv4 + 12 bits sequence;
 * https://github.com/twitter/snowflake
 */
@Slf4j
public class DistributedIdService {

    /**
     * Bits definition for each part.
     */
    private static final long UNUSED_BITS = 1L;
    private static final long TIMESTAMP_BITS = 41L;
    private static final long IP_ADDRESS_BITS = 10L;
    private static final long SEQUENCE_BITS = 12L;

    // last timestamp for the IDs
    // id sequence
    // reference material of 'time stamp' is '2022-08-10'. its value can't be modified after initialization
    private final static long EPOCH = 1660106095373L;


    /**
     * max value of sequence - 4096
     */
    private static final long SEQUENCE_MAX = -1L ^ (-1L << SEQUENCE_BITS);

    /**
     * left shift bits of timestamp, ip address and sequence.
     */
    private final static long IP_ADDRESS_SHIFT = SEQUENCE_BITS;
    private final static long TIMESTAMP_SHIFT = IP_ADDRESS_BITS + SEQUENCE_BITS;

    private volatile Long lastTimestamp = -1L;
    private volatile long sequence = 0L;

    /**
     * generate an unique id on distributed environment.
     *
     * @return
     */
    public synchronized ID nextId() {
        long currentTimeStamp = timeGenerator();

        if (currentTimeStamp < lastTimestamp) {
            throw new IllegalStateException("System clock is morning backward, reject request.");
        }

        if (currentTimeStamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MAX;
            if (sequence == 0) {
                currentTimeStamp = waitNextMillis(currentTimeStamp);
            }
        } else {
            // reset to 0 for next period/millisecond.
            sequence = 0L;
        }

        // mark down the timestamp.
        lastTimestamp = currentTimeStamp;
        String localIpv4 = IpUtils.getLocalIpv4();
        Long ipNumber = IpUtils.ipToNumber(localIpv4);
        log.info("timestamp: {}, ipNumber: {}, sequence: {}", currentTimeStamp, ipNumber, sequence);

        Long idValue = ((currentTimeStamp - EPOCH) << TIMESTAMP_SHIFT)  // timestamp
                | (ipNumber << IP_ADDRESS_SHIFT)                // ip address
                | sequence;                                     // sequence
        String stringValue = String.format("%d-%041d-%010d-%012d", 0, currentTimeStamp - EPOCH, ipNumber, sequence);

        return ID.of(idValue, stringValue);
    }

    private long timeGenerator() {
        return System.currentTimeMillis();
    }

    // get next millisecond
    private long waitNextMillis(long lastTimestamp) {
        long timestamp = timeGenerator();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGenerator();
        }
        return timestamp;
    }

    @Getter
    static class ID {
        private long value;
        private String displayValue;

        private ID(long value, String displayValue) {
            this.value = value;
            this.displayValue = displayValue;
        }

        public static ID of(long value, String displayValue) {
            return new ID(value, displayValue);
        }
    }
}
