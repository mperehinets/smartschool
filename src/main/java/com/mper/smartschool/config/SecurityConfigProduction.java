package com.mper.smartschool.config;

import com.mper.smartschool.security.JwtAuthenticationFilter;
import com.mper.smartschool.security.JwtAuthorizationFilter;
import com.mper.smartschool.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("production")
@RequiredArgsConstructor
public class SecurityConfigProduction extends WebSecurityConfigurerAdapter {

    public final static String TOKEN_PREFIX = "Bearer ";
    public final static String HEADER_NAME = "Authorization";
    private static final String[] WHITELIST = {"/smartschool/auth/login"};

    private final MessageSource messageSource;

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider());
        authenticationFilter.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/smartschool/auth/login", "POST"));
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/smartschool/**")
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(jwtAuthenticationFilter())
                .addFilter(new JwtAuthorizationFilter(authenticationManagerBean(), jwtTokenProvider(), messageSource));
    }
}
