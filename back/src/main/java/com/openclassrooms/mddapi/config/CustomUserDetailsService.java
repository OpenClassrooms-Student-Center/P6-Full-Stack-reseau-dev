package com.openclassrooms.mddapi.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Injection du repository UserRepository pour accéder à la base de données
    @Autowired
    private UserRepository userRepository;

    // Méthode principale pour charger un utilisateur par son email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Recherche de l'utilisateur dans la base de données via son email
        Optional<User> userOptional = userRepository.findByEmail(email);

        // Si l'utilisateur n'est pas trouvé, on lève une exception UsernameNotFoundException
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        // Récupération de l'utilisateur s'il est trouvé dans la base de données
        User user = userOptional.get();

        // Récupération des autorités (rôles) de l'utilisateur, ici, un rôle simple "USER"
        List<GrantedAuthority> authorities = getGrantedAuthorities("USER");

        // Création et retour d'un objet UserDetails contenant l'email, le mot de passe et les rôles
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities);
    }

    // Méthode pour créer et renvoyer la liste des rôles (authorities) de l'utilisateur
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        // Initialisation d'une liste d'autorités
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Ajout d'une autorité (rôle) avec le rôle passé en paramètre (ici "USER")
        authorities.add(new SimpleGrantedAuthority(role));

        // Retour de la liste des autorités
        return authorities;
    }
}
