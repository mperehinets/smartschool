package com.mper.smartschool.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

import static com.mper.smartschool.config.SecurityConfigProduction.TOKEN_PREFIX;

public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expiration}")
    private Long expiration;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String generateToken(UserPrincipal user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());

        claims.put("id", user.getId());

        String rolesName = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put("roles", rolesName);
        claims.put("firstName", user.getFirstName());
        claims.put("secondName", user.getSecondName());
        claims.put("avatarName", user.getAvatarName());
        return TOKEN_PREFIX + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public UserPrincipal parseToken(String token) {
        try {
            token = token.replace(TOKEN_PREFIX, "");
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            return UserPrincipal.builder()
                    .id(body.get("id", Long.class))
                    .firstName((String) body.get("firstName"))
                    .secondName((String) body.get("lastName"))
                    .avatarName((String) body.get("avatarName"))
                    .email(body.getSubject())
                    .password("")
                    .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList((String) body.get("roles")))
                    .build();
        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }
}
