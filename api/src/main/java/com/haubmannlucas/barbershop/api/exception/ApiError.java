package com.haubmannlucas.barbershop.api.exception;

public record ApiError(
    Integer status,
    String code,
    String message
){}
