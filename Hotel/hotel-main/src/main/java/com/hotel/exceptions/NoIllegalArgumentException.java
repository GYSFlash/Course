package com.hotel.exceptions;

public class NoIllegalArgumentException extends RuntimeException{
    public NoIllegalArgumentException(String message) {
        super(message);
    }
}