package com.mper.smartschool.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mper.smartschool.config.MessageConfig;
import com.mper.smartschool.dto.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mper.smartschool.config.SecurityConfigProduction.HEADER_NAME;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        AuthenticationRequest authenticationRequest = new ObjectMapper()
                .readValue(req.getInputStream(), AuthenticationRequest.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {
        UserPrincipal user = (UserPrincipal) auth.getPrincipal();
        String token = jwtTokenProvider.generateToken(user);
        res.addHeader(HEADER_NAME, token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req,
                                              HttpServletResponse res,
                                              AuthenticationException failed) throws IOException {
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                messages.getMessage("Unauthorized", MessageConfig.resolveLocale(req)));
    }
}
