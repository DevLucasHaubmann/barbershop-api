package com.haubmannlucas.barbershop.api.domain.shopservices;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity(name="ShopService")
@Table(name="shop_services")
public class ShopServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="service_name",  nullable = false, unique = true)
    private String serviceName;

    @Column(name="service_description",   nullable = false)
    private String serviceDescription;

    @Column(name="duration_minutes",   nullable = false)
    private Integer serviceDuration;

    @Column(name="price", nullable = false)
    private BigDecimal servicePrice;

    @Column(name="active", nullable = false)
    private boolean active = true;

    public ShopServiceEntity() {}

    public ShopServiceEntity(Builder builder){
        this.id = builder.id;
        this.serviceName = builder.serviceName;
        this.serviceDescription = builder.serviceDescription;
        this.serviceDuration = builder.serviceDuration;
        this.servicePrice = builder.servicePrice;
        this.active = builder.active;
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
        private Boolean active = true;

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

        public Builder serviceDuration(Integer duration) {
            this.serviceDuration = duration;
            return this;
        }

        public Builder servicePrice(BigDecimal price) {
            this.servicePrice = price;
            return this;
        }

        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        public ShopServiceEntity build() {
            return new ShopServiceEntity(this);
        }

    }

    public Long getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public Integer getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(Integer serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
