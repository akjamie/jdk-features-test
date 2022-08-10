package org.akj.snowflake.exception;

public class InvalidIpAddressException extends RuntimeException {
    private String code;
    private String message;

    public InvalidIpAddressException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
