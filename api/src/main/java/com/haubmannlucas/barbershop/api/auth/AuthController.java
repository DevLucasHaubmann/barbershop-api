package com.haubmannlucas.barbershop.api.auth;

import com.haubmannlucas.barbershop.api.domain.refreshtoken.RefreshTokenEntity;
import com.haubmannlucas.barbershop.api.domain.refreshtoken.RefreshTokenRepository;
import com.haubmannlucas.barbershop.api.domain.refreshtoken.RefreshTokenService;
import com.haubmannlucas.barbershop.api.domain.refreshtoken.dto.AuthTokenPayload;
import com.haubmannlucas.barbershop.api.domain.refreshtoken.dto.RefreshTokenRequestDTO;
import com.haubmannlucas.barbershop.api.domain.refreshtoken.dto.RefreshTokenResponseDTO;
import com.haubmannlucas.barbershop.api.domain.user.UserEntity;
import com.haubmannlucas.barbershop.api.domain.user.UserRepository;
import com.haubmannlucas.barbershop.api.domain.user.dto.UserLoginRequestDTO;
import com.haubmannlucas.barbershop.api.domain.user.dto.UserLoginResponseDTO;
import com.haubmannlucas.barbershop.api.exception.TokenRefreshException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtils;
    private RefreshTokenService refreshTokenService;
    private UserRepository userRepository;

    public AuthController() {}

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtils, RefreshTokenService refreshTokenService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> authenticateUser(@Valid @RequestBody UserLoginRequestDTO requestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.email(), requestDTO.password())
        );

        String email = extractEmail(authentication);

        AuthTokenPayload tokenPayload = refreshTokenService.generateTokensForUser(email);

        ResponseCookie jwtCookie = jwtUtils.getJwtCookie(tokenPayload.jwt());
        UserLoginResponseDTO response = UserLoginResponseDTO.builder()
                .email(email)
                .token(tokenPayload.refreshToken())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails != null) {
            refreshTokenService.deleteByUsername(userDetails.getUsername());
        }

        ResponseCookie cleanCookie = jwtUtils.getCleanJwtCookie();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cleanCookie.toString())
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO requestDTO) {

        String newJwtToken = refreshTokenService.processRefresh(requestDTO.token());

        ResponseCookie jwtCookie = jwtUtils.getJwtCookie(newJwtToken);

        RefreshTokenResponseDTO response = RefreshTokenResponseDTO.builder()
                .token(newJwtToken)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(response);
    }

    private String extractEmail(Authentication authentication) {
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }
}