package com.utn.exception;

public class CharNotFoundException extends RuntimeException {
    public CharNotFoundException() {
        super("Not found character");
    }
}
