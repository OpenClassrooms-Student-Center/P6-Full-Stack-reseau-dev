package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService  {
    public void register(SignupRequest signupRequest);
    public JwtResponse authenticate(LoginRequest loginRequest);
    public UserDto getConnectedUserInformation();
    public User findUserByIdentifier(String identifier);
    public User getConnectedUser();

}
