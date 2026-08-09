package com.haubmannlucas.barbershop.api.domain.user.dto;

import com.haubmannlucas.barbershop.api.domain.user.UserRoles;

public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String ddd,
        String phoneNumber,
        UserRoles role,
        Boolean active
){}
