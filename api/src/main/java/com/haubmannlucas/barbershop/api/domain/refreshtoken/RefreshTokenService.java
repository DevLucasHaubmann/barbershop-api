package com.haubmannlucas.barbershop.api.domain.refreshtoken;

import com.haubmannlucas.barbershop.api.auth.JwtUtil;
import com.haubmannlucas.barbershop.api.domain.refreshtoken.dto.AuthTokenPayload;
import com.haubmannlucas.barbershop.api.domain.user.UserEntity;
import com.haubmannlucas.barbershop.api.domain.user.UserRepository;
import com.haubmannlucas.barbershop.api.exception.TokenRefreshException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtils;

    @Value("${jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    public RefreshTokenService(RefreshTokenRepository repo, UserRepository userRepo, JwtUtil jwtUtils) {
        this.refreshTokenRepository = repo;
        this.userRepository = userRepo;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public RefreshTokenEntity createRefreshToken(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var token = new RefreshTokenEntity();
        token.setUser(user);
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        token.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(token);
    }

    @Transactional
    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException();
        }
        return token;
    }

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public void deleteByUsername(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        refreshTokenRepository.deleteByUser(user);
    }

    @Transactional
    public String processRefresh(String requestRefreshToken) {
        return refreshTokenRepository.findByToken(requestRefreshToken)
                .map(this::verifyExpiration)
                .map(RefreshTokenEntity::getUser)
                .map(user -> jwtUtils.generateToken(user.getEmail()))
                .orElseThrow(() -> new TokenRefreshException("Refresh token is not in database."));
    }

    @Transactional
    public AuthTokenPayload generateTokensForUser(String email) {
        String jwt = jwtUtils.generateToken(email);

        RefreshTokenEntity refreshToken = createRefreshToken(email);

        return new AuthTokenPayload(jwt, refreshToken.getToken());
    }

}
