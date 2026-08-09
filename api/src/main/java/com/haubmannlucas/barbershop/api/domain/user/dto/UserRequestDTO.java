package com.haubmannlucas.barbershop.api.domain.user.dto;

import com.haubmannlucas.barbershop.api.domain.user.UserRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "First name is required.")
        @Size(max=20, message = "First name must be at maximum of 20 characters.")
        String firstName,

        @NotBlank(message = "Last name is required.")
        @Size(max=20, message = "Last name must be at maximum of 20 characters.")
        String lastName,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be a valid format.")
        String email,

        @NotBlank(message = "Password is required.")
        @Size(min=8, max=20, message = "Password must be between 8 and 20 characters.")
        String password,

        @Pattern(regexp="^[0-9]{2}$", message = "Invalid ddd number")
        String ddd,

        @Pattern(regexp="^[0-9]{8,9}$", message = "Invalid phone number")
        String phoneNumber,

        UserRoles role,

        Boolean active
){}
