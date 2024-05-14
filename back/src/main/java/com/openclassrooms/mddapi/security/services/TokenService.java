package com.openclassrooms.mddapi.security.services;

import com.openclassrooms.mddapi.service.MddUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;
    private final MddUserService mddUserService;

    public TokenService(JwtEncoder encoder, JwtDecoder decoder, MddUserService mddUserService) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.mddUserService = mddUserService;
    }

    public String generateTokenFromAuthentication(Authentication authentication) {
        return generateToken(authentication.getName(), authentication.getAuthorities());
    }

    public String generateTokenFromUsername(String username) {
        UserDetails userDetails = mddUserService.loadUserByUsername(username);
        return generateToken(userDetails.getUsername(), userDetails.getAuthorities());
    }

    public String decodeTokenUsername(String token) {
        return this.decoder.decode(token.substring(7)).getSubject();
    }

    private String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Instant now = Instant.now();
        long expiry = 180L;
        String scope = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(username)
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}