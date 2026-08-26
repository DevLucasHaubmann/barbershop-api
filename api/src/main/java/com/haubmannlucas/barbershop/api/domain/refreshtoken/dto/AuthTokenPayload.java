package com.haubmannlucas.barbershop.api.domain.refreshtoken.dto;

public record AuthTokenPayload(String jwt, String refreshToken) {}