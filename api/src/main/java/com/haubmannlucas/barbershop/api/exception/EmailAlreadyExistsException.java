package com.haubmannlucas.barbershop.api.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    private static final String ERROR_CODE = "EMAIL_ALREADY_EXISTS";

    public EmailAlreadyExistsException(String email) {
        super(String.format("The email '%s' is already registered.", email));
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}