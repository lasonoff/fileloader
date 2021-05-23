package ru.yauroff.test.fileloader.service;

public class ParamsNotValidException extends Exception {

    public ParamsNotValidException(String message) {
        super(message);
    }

    public ParamsNotValidException(String message, Exception exception) {
        super(message, exception);
    }
}
