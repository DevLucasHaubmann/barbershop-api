package com.haubmannlucas.barbershop.api.auth;

import com.haubmannlucas.barbershop.api.domain.user.dto.UserLoginRequestDTO;
import com.haubmannlucas.barbershop.api.domain.user.dto.UserLoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> authenticateUser(@Valid @RequestBody UserLoginRequestDTO requestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.email(),
                        requestDTO.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        assert userDetails != null;
        String jwt = jwtUtils.generateToken(userDetails.getUsername());

        UserLoginResponseDTO response = UserLoginResponseDTO.builder()
                .token(jwt)
                .email(userDetails.getUsername())
                .build();

        return ResponseEntity.ok(response);
    }
}