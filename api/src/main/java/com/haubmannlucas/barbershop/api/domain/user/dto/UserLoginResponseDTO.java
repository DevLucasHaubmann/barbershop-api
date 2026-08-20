package com.haubmannlucas.barbershop.api.domain.user.dto;

public class UserLoginResponseDTO {
    private String token;
    private String email;

    public UserLoginResponseDTO() {}

    private UserLoginResponseDTO(Builder builder) {
        this.token = builder.token;
        this.email = builder.email;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getToken() { return token; }
    public String getEmail() { return email; }

    public static class Builder {
        private String token;
        private String email;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public UserLoginResponseDTO build() {
            return new UserLoginResponseDTO(this);
        }
    }
}