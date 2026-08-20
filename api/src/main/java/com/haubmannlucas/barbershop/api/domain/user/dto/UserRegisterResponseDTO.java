package com.haubmannlucas.barbershop.api.domain.user.dto;

import com.haubmannlucas.barbershop.api.domain.user.UserRoles;

public class UserRegisterResponseDTO{

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String ddd;
    private String phoneNumber;
    private UserRoles role;
    private Boolean active;

    public UserRegisterResponseDTO() {}

    public UserRegisterResponseDTO(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.ddd = builder.ddd;
        this.phoneNumber = builder.phoneNumber;
        this.role = builder.role;
        this.active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{

        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String ddd;
        private String phoneNumber;
        private UserRoles role;
        private Boolean active;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder ddd(String ddd) {
            this.ddd = ddd;
            return this;
        }
        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public Builder role(UserRoles role) {
            this.role = role;
            return this;
        }
        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }
        public UserRegisterResponseDTO build() {
            return new UserRegisterResponseDTO(this);
        }
    }
}
