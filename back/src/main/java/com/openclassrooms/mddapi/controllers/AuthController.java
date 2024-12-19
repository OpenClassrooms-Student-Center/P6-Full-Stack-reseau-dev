package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.services.interfaces.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse;
        jwtResponse = userService.authenticate(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
            userService.register(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("me")
    public ResponseEntity<?> getConnectedUserInformation() {
        UserDto userDto;
        userDto = userService.getConnectedUserInformation();
        return ResponseEntity.ok(userDto);
    }
}

