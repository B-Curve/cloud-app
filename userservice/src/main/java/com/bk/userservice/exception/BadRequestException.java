package com.bk.userservice.exception;

public class BadRequestException extends Exception {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Exception e) {
        super(message, e);
    }

    public BadRequestException(Exception e) {
        super(e);
    }

}
