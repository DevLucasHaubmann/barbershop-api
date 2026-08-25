package com.haubmannlucas.barbershop.api.domain.refreshtoken;

import com.haubmannlucas.barbershop.api.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUser(UserEntity user);
}
