package com.haubmannlucas.barbershop.api.domain.refreshtoken.dto;

import com.haubmannlucas.barbershop.api.domain.user.UserEntity;

import java.time.Instant;

public class RefreshTokenResponseDTO {

    private Long id;
    private UserEntity user;
    private String token;
    private Instant expiryDate;

    public RefreshTokenResponseDTO() {}

    public RefreshTokenResponseDTO(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.token = builder.token;
        this.expiryDate = builder.expiryDate;
    }

    public Long getId() { return id; }
    public UserEntity getUser() { return user; }
    public String getToken() { return token; }
    public Instant getExpiryDate() { return expiryDate; }

    public static Builder builder() { return new Builder(); }

    public static class Builder{

        private Long id;
        private UserEntity user;
        private String token;
        private Instant expiryDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder user(UserEntity user) {
            this.user = user;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder expiryDate(Instant expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public RefreshTokenResponseDTO build() {
            return new RefreshTokenResponseDTO(this);
        }
    }
}
