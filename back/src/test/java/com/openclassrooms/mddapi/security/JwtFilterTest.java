package com.openclassrooms.mddapi.security;

import com.openclassrooms.mddapi.models.UserPrincipal;
import com.openclassrooms.mddapi.services.impl.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    private JwtServiceImpl jwtServiceImpl;
    @Mock
    private ApplicationContext context;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    ServletContext servletContext;

    private String token ="token";
    @InjectMocks
    JwtFilter jwtFilter;


    @Mock
    FilterChain chain;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void doFilterInternal() {
    }

    @Test
    void dontNeedAuthorisation_login() throws ServletException, IOException {
        String path = "/api/auth/login";
        when(request.getServletPath()).thenReturn(path);
        jwtFilter.doFilterInternal(request, response, chain);
        verify(chain,times(1)).doFilter(request,
                response);
    }

    @Test
    void dontNeedAuthorisation_register() throws ServletException, IOException {
        String path = "/api/auth/register";
        when(request.getServletPath()).thenReturn(path);
        jwtFilter.doFilterInternal(request, response, chain);
        verify(chain,times(1)).doFilter(request,
                response);
    }



    @Test
    void validateToken() {
    }
}