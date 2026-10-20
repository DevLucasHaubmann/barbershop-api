package com.haubmannlucas.barbershop.api.exception;

public class NameAlreadyExistsException extends RuntimeException {
    public NameAlreadyExistsException(){
        super("Name already exists");
    }

    public NameAlreadyExistsException(String name) {
        super("Service name '" + name + "' is already in use.");
    }
}
