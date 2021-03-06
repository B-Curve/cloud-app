package com.bk.client.exception;

public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Exception e) {
        super(message, e);
    }

    public NotFoundException(Exception e) {
        super(e);
    }

}
