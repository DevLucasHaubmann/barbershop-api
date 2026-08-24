package com.haubmannlucas.barbershop.api.domain.user;

import com.haubmannlucas.barbershop.api.domain.user.dto.UserRegisterRequestDTO;
import com.haubmannlucas.barbershop.api.domain.user.dto.UserRegisterResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("auth/register")
    public ResponseEntity<UserRegisterResponseDTO> registerUser(@Valid @RequestBody UserRegisterRequestDTO requestDto){
        UserRegisterResponseDTO responseDTO = userService.createUser(requestDto);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
