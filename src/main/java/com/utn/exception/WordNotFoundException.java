package com.utn.exception;

public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException() {
        super("Not found any Word");
    }
}
