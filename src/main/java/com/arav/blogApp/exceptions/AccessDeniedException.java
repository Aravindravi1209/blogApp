package com.arav.blogApp.exceptions;

public class AccessDeniedException extends Exception{
    public AccessDeniedException(String message) {
        super(message);
    }
}
