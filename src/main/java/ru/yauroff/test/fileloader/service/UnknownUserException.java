package ru.yauroff.test.fileloader.service;

public class UnknownUserException extends Exception {

    public UnknownUserException(String message) {
        super(message);
    }

    public UnknownUserException(String message, Exception exception) {
        super(message, exception);
    }
}
