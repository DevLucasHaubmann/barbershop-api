package com.haubmannlucas.barbershop.api.exception;

public class TokenRefreshException extends RuntimeException {

    public TokenRefreshException() {
        super("Refresh token was expired. Please login again.");
    }

    public TokenRefreshException(String message) { super(message); }
}
