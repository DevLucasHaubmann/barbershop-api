package com.haubmannlucas.barbershop.api.domain.shopservices.dto;

import java.math.BigDecimal;

public class ShopServiceResponseDTO {

    private Long id;
    private String serviceName;
    private String serviceDescription;
    private Integer serviceDuration;
    private BigDecimal servicePrice;
    private Boolean active;

    public ShopServiceResponseDTO() {}

    public ShopServiceResponseDTO(Builder builder) {
        this.id = builder.id;
        this.serviceName = builder.serviceName;
        this.serviceDescription = builder.serviceDescription;
        this.serviceDuration = builder.serviceDuration;
        this.servicePrice = builder.servicePrice;
        this.active = builder.active;
    }

    public Long getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public Integer getServiceDuration() {
        return serviceDuration;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public Boolean getActive() {
        return active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long id;
        private String serviceName;
        private String serviceDescription;
        private Integer serviceDuration;
        private BigDecimal servicePrice;
        private Boolean active;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public Builder serviceDescription(String serviceDescription) {
            this.serviceDescription = serviceDescription;
            return this;
        }

        public Builder serviceDuration(Integer serviceDuration) {
            this.serviceDuration = serviceDuration;
            return this;
        }

        public Builder servicePrice(BigDecimal servicePrice) {
            this.servicePrice = servicePrice;
            return this;
        }

        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        public ShopServiceResponseDTO build() {
            return new ShopServiceResponseDTO(this);
        }
    }
}
