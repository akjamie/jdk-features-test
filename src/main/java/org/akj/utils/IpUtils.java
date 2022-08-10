package org.akj.utils;

import lombok.SneakyThrows;
import org.akj.snowflake.exception.InvalidIpAddressException;

import java.net.InetAddress;
import java.util.regex.Pattern;

public class IpUtils {
    // IPV4 validation
    private static final String REG_IPV4_ITEM = "(([0-9])|([1-9]\\d)|(([1]\\d{2})|(([2][0-4]\\d)|([2][5][0-5]))))";
    private static final String REG_IPV4 = REG_IPV4_ITEM + "(." + REG_IPV4_ITEM + "){3}";
    private static final Pattern PATTERN_IPV4 = Pattern.compile(REG_IPV4);

    /**
     * Convert Ip address from String to Long.
     *
     * @param ip
     * @return
     */
    public static long ipToNumber(String ip) {
        if (!PATTERN_IPV4.matcher(ip).matches()) {
            throw new InvalidIpAddressException("error-001", "Invalid IPv4 address received, " + ip);
        }

        String[] parts = ip.split("\\.");
        Long number = 0L;

        for (int i = 0; i < parts.length; i++) {
            number += (Long.parseLong(parts[i]) << (24 - i * 8));
        }

        return number;
    }

    /**
     * Convert the ip address from Long format to String.
     *
     * @param ipNumber
     * @return
     */
    public static String numberToIp(Long ipNumber) {
        String result = String.format("%d.%d.%d.%d", ipNumber >>> 24, (ipNumber & 0x00FFFFFF) >>> 16,
                (ipNumber & 0x0000FFFF) >>> 8, ipNumber & 0x000000FF);
        return result;
    }

    @SneakyThrows
    public static String getLocalIpv4() {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
