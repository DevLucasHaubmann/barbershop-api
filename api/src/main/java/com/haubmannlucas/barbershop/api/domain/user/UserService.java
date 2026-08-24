package com.haubmannlucas.barbershop.api.domain.user;

import com.haubmannlucas.barbershop.api.domain.user.dto.UserRegisterRequestDTO;
import com.haubmannlucas.barbershop.api.domain.user.dto.UserRegisterResponseDTO;
import com.haubmannlucas.barbershop.api.exception.EmailAlreadyExistsException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository,  PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostMapping
    public UserRegisterResponseDTO createUser(@Valid @RequestBody UserRegisterRequestDTO request){

        logger.info("Starting user registration for email: {}", request.email());

        UserEntity entity = UserEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .ddd(request.ddd())
                .phoneNumber(request.phoneNumber())
                .role(UserRoles.CLIENT)
                .active(true)
                .build();
        try {
            UserEntity savedEntity = userRepository.save(entity);
            logger.info("User successfully registered with ID: {}", savedEntity.getId());

            return UserRegisterResponseDTO.builder()
                    .id(savedEntity.getId())
                    .firstName(savedEntity.getFirstName())
                    .lastName(savedEntity.getLastName())
                    .email(savedEntity.getEmail())
                    .ddd(savedEntity.getDdd())
                    .phoneNumber(savedEntity.getPhoneNumber())
                    .role(savedEntity.getRole())
                    .active(savedEntity.getActive())
                    .build();
        }catch (DataIntegrityViolationException e){
            if(e.getMessage() != null && e.getMessage().contains("email")){
                logger.warn("Registration failed: Email {} is already in use", request.email());
                throw new EmailAlreadyExistsException("This email already exists.");
            }
            logger.warn("Registration failed: Internal Server Error.");
            throw e;
        }
    }
}
