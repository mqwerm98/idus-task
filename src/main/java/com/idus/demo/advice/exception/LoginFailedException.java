package com.idus.demo.advice.exception;

public class LoginFailedException extends RuntimeException{

    public LoginFailedException() {
        super();
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
