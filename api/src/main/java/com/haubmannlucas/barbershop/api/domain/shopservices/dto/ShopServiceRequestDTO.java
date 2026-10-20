package com.haubmannlucas.barbershop.api.domain.shopservices.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ShopServiceRequestDTO(

   @NotBlank(message = "Service name is required.")
   String serviceName,

   @NotBlank(message = "Service description is required.")
   String serviceDescription,

   @NotNull(message = "Service serviceDuration is required.")
   @Positive(message = "Service serviceDuration must be a positive value.")
   Integer serviceDuration,

   @NotNull(message = "Service servicePrice is required.")
   @Positive(message = "Service servicePrice must be a positive value.")
   @Digits(integer = 8, fraction = 2, message = "Service servicePrice must have between 0 to 8 digits.")
   BigDecimal servicePrice)
{}
