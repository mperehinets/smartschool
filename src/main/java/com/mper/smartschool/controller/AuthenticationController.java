package com.mper.smartschool.controller;

import com.mper.smartschool.dto.AuthenticationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/smartschool/auth/login")
public class AuthenticationController {

    @PostMapping()
    public void login(@RequestBody AuthenticationRequest authenticationRequest) {
    }
}
