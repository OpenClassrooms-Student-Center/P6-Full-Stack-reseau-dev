package com.openclassrooms.mddapi.service;


import com.openclassrooms.mddapi.model.DBUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.repository.DBUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DBUserRepository DBUserRepository;

    // On implémente la méthode loadUserByUsername qui va nous permettre de récupérer l'utilisateur lors de la connexion
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<DBUser> user = DBUserRepository.findByEmail(email);

        if(user.isPresent()) {
            DBUser existingUser = user.get();

            return new User(existingUser.getEmail(), existingUser.getPassword(), getGrantedAuthorities("USER"));
        }
        else {
            throw new UsernameNotFoundException(format("User: %s not found in db", email));
        }

    }

    /*
       * We add a default role USER to the user

     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}