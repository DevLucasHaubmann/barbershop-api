package com.haubmannlucas.barbershop.api.domain.refreshtoken;

import com.haubmannlucas.barbershop.api.domain.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity(name = "RefreshToken")
@Table(name = "refresh_token")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expiry_date",nullable = false)
    private Instant expiration;

    public RefreshTokenEntity() {}

    private RefreshTokenEntity(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.token = builder.token;
        this.expiration = builder.expiration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private UserEntity user;
        private String token;
        private Instant expiration;

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

        public Builder expiration(Instant expiration) {
            this.expiration = expiration;
            return this;
        }

        public RefreshTokenEntity build() {
            return  new RefreshTokenEntity(this);
        }

    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }
}
