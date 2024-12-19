package com.openclassrooms.mddapi.security;


import com.openclassrooms.mddapi.models.UserPrincipal;
import com.openclassrooms.mddapi.services.impl.JwtServiceImpl;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtServiceImpl jwtServiceImpl;
    private final ApplicationContext context;
    private final JwtUtils jwtUtils;

    private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();

        if (dontNeedAuthorisation(request, response, filterChain, path))
            return;  // Continuer la chaîne sans vérification JWT pour ces routes

        String token = jwtUtils.extractTokenFromRequest(request);
        String identifier = null;
        logger.debug("token: " + token);
        if (token!=null && !token.isEmpty()){
            identifier = jwtServiceImpl.extractIdentifier(token);
        }
        logger.debug("identifier: " + identifier);

        if (hasToBoAuthenticated(identifier)) {
            logger.debug("check authentication: " + identifier);
            UserPrincipal userPrincipal = (UserPrincipal) context.
                    getBean(UserDetailsService.class).
                    loadUserByUsername(identifier);
            logger.debug("userPrincipal: " + userPrincipal);
            validateToken(request, token, userPrincipal);
        }
        filterChain.doFilter(request, response);
    }

    public boolean dontNeedAuthorisation(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String path) throws IOException, ServletException {
        if (path.equals("/api/auth/login") || path.equals("/api/auth/register")) {
            logger.debug("Filter : register called");
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }


    public void validateToken(HttpServletRequest request, String token, UserPrincipal userPrincipal) {
        boolean isValidToken = jwtServiceImpl.hasTokenNotExpired(token);
        if (isValidToken) {
            logger.warn("Roles for user {}: {}", userPrincipal.getUsername(), userPrincipal.getAuthorities());

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userPrincipal,
                    null, userPrincipal.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.
                    getContext().
                    setAuthentication(authToken);
        }
    }





    private static boolean hasToBoAuthenticated(String identifier) {
        return identifier == null || SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
