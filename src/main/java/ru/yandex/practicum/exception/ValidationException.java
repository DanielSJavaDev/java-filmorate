package ru.yandex.practicum.exception;

public class ValidationException extends Throwable {

    public ValidationException(final String message){
        super(message);
    }
}
