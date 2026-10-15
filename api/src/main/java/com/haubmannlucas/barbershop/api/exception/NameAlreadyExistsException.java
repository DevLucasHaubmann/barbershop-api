package com.haubmannlucas.barbershop.api.exception;

public class NameAlreadyExistsException extends RuntimeException {
    public NameAlreadyExistsException(){
        super("Name already exists");
    }
}
