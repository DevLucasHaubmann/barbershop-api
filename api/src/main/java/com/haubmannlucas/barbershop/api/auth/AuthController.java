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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> authenticateUser(@Valid @RequestBody UserLoginRequestDTO requestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.email(), requestDTO.password())
        );

        String email = ((UserDetails) authentication.getPrincipal()).getUsername();

        AuthTokenPayload tokens = refreshTokenService.generateTokensForUser(email);

        ResponseCookie jwtCookie = jwtUtils.getJwtCookie(tokens.jwt());
        UserLoginResponseDTO response = UserLoginResponseDTO.builder()
                .email(email)
                .token(tokens.refreshToken())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails != null) {
            refreshTokenService.deleteByUsername(userDetails.getUsername());
        }

        ResponseCookie cleanCookie = jwtUtils.getCleanJwtCookie();

        return ResponseEntity.ok()
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

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(response);
    }
}