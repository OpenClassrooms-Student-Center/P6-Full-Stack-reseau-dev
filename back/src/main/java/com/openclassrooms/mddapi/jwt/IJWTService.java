package com.openclassrooms.mddapi.jwt;

import org.springframework.security.core.Authentication;

public interface IJWTService {
    String generateToken(String name);
}
