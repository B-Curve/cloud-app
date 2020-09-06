package com.bk.client.exception;

public class UnauthorizedException extends Exception {

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Exception e) {
        super(message, e);
    }

    public UnauthorizedException(Exception e) {
        super(e);
    }

}
