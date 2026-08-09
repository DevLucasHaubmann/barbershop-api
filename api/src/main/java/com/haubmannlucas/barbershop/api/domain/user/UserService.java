package com.haubmannlucas.barbershop.api.domain.user;

import com.haubmannlucas.barbershop.api.domain.user.dto.UserRequestDTO;
import com.haubmannlucas.barbershop.api.domain.user.dto.UserResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserRequestDTO request){
        UserEntity entity = new UserEntity();

        entity.setFirstName(request.firstName());
        entity.setLastName(request.lastName());
        entity.setEmail(request.email());
        entity.setPassword(encoder.encode(request.password()));
        entity.setDdd(request.ddd());
        entity.setPhoneNumber(request.phoneNumber());
        entity.setRole(UserRoles.CLIENT);
        entity.setActive(true);

        UserEntity savedEntity = userRepository.save(entity);

        return new UserResponseDTO(
                savedEntity.getId(),
                savedEntity.getFirstName(),
                savedEntity.getLastName(),
                savedEntity.getEmail(),
                savedEntity.getDdd(),
                savedEntity.getPhoneNumber(),
                savedEntity.getRole(),
                savedEntity.getActive()
        );
    }

    @GetMapping
    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }
}
