package com.mper.smartschool.security;

import com.mper.smartschool.security.dto.AuthenticationRequest;
import com.mper.smartschool.security.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/smartschool/auth/login")
@RequiredArgsConstructor
public class AuthenticationEntryPoint {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping()
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            return new AuthenticationResponse(jwtTokenProvider
                    .generateToken((UserPrincipal) authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    authenticationRequest.getEmail(),
                                    authenticationRequest.getPassword())).getPrincipal()));
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Unauthorized");
        }
    }
}
