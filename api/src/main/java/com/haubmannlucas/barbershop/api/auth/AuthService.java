package com.haubmannlucas.barbershop.api.auth;

import com.haubmannlucas.barbershop.api.domain.user.UserEntity;
import com.haubmannlucas.barbershop.api.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        logger.debug("Attempting to load user by email: {}", email);
        try {
            UserEntity userEntity = userRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        logger.warn("User not found with email: {}", email);
                        return new UsernameNotFoundException("User not found with email: " + email);
                    });
            logger.debug("Successfully loaded user: {}", email);
            return User.builder()
                    .username(userEntity.getEmail())
                    .password(userEntity.getPassword())
                    .roles(userEntity.getRole().name())
                    .disabled(!userEntity.getActive())
                    .build();
        } catch (UsernameNotFoundException ex) {
            throw ex;
        }
    }
}