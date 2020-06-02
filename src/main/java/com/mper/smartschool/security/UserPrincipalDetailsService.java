package com.mper.smartschool.security;

import com.mper.smartschool.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserPrincipalDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserPrincipalDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + username));
        log.info("IN loadByUsername - user with username: {} successfully loaded", username);

        return new UserPrincipal(user);
    }

}
