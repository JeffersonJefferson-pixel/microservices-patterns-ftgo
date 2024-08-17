package com.example.ftgo.common;

public class UnsupportedStateTransitionException extends RuntimeException {
    public UnsupportedStateTransitionException(String state) {
        super("current state: " + state);
    }
}
