package com.gabriella.databases.exceptions.email;

public class SendgridRequestException extends RuntimeException {

    public SendgridRequestException(String message) {
        super(message);
    }
}
