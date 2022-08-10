package org.akj.snowflake;

import org.akj.utils.IpUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

class DistributedIdServiceTest {



    @Test
    void ipToNumber() {
        long ipToNumber = IpUtils.ipToNumber("127.0.0.1");

        Assertions.assertEquals(2130706433, ipToNumber);
    }

    @Test
    void numberToIp() {
        String ip = IpUtils.numberToIp(3232261222L);

        Assertions.assertEquals("192.168.100.102", ip);
    }

    @Test
    void getNextId(){
        DistributedIdService service = new DistributedIdService();
        IntStream.range(0, 1 << 12).parallel().forEach(i -> {
            DistributedIdService.ID id = service.nextId();
            System.out.println(id.getValue());
            System.out.println(id.getDisplayValue());
        });
    }

}