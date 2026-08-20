package com.haubmannlucas.barbershop.api.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDTO(

        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be a valid format.")
        String email,

        @NotBlank(message = "Password is required.")
        String password
) {
        public String getEmail() {
                return email;
        }

        public String getPassword() {
                return password;
        }
}
