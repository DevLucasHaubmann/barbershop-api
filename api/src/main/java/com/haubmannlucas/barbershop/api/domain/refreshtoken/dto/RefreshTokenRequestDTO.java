package com.haubmannlucas.barbershop.api.domain.refreshtoken.dto;

import com.haubmannlucas.barbershop.api.domain.user.UserEntity;

import java.time.Instant;

public record RefreshTokenRequestDTO(

        UserEntity user,
        String token,
        Instant expiryDate
) {

    public String token() {
        return token;
    }

    public Instant expiryDate() {
        return expiryDate;
    }

    public UserEntity user() {
        return user;
    }
}

