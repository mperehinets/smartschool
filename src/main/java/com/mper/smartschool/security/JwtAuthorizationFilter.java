package com.mper.smartschool.security;

import com.mper.smartschool.config.MessageConfig;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mper.smartschool.config.SecurityConfigProduction.HEADER_NAME;
import static com.mper.smartschool.config.SecurityConfigProduction.TOKEN_PREFIX;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MessageSource messageSource;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtTokenProvider jwtTokenProvider,
                                  MessageSource messageSource) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.messageSource = messageSource;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) {
        String token = req.getHeader(HEADER_NAME);
        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    messageSource.getMessage("JWT.missing", null, MessageConfig.resolveLocale(req)));
            return;
        }
        var parsedUser = jwtTokenProvider.parseToken(token);
        if (parsedUser == null) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    messageSource.getMessage("JWT.invalid", null, MessageConfig.resolveLocale(req)));
            return;
        }
        var authentication = new UsernamePasswordAuthenticationToken(parsedUser,
                null,
                parsedUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
}
