package com.openclassrooms.mddapi.service.user;

import com.openclassrooms.mddapi.dto.ResponseDTO;
import com.openclassrooms.mddapi.dto.TokenDTO;
import com.openclassrooms.mddapi.exception.AuthException;
import com.openclassrooms.mddapi.exception.RegistrationException;
import com.openclassrooms.mddapi.jwt.JWTService;
import jakarta.persistence.EntityExistsException;

import com.openclassrooms.mddapi.dto.DBUserDTO;
import com.openclassrooms.mddapi.model.DBUser;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import com.openclassrooms.mddapi.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.lang.String.format;

@Service
public class DBUserService implements IDBUserService {

    @Autowired
    private DBUserRepository dbUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private DateUtils DateUtils;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseDTO register(final DBUserDTO userDTO) throws EntityExistsException{
        if(!checkPassword(userDTO)){
            throw new AuthException("Le mot de passe doit ne respecte pas les règles de sécurité");
        }
        DBUser user = modelMapper.map(userDTO, DBUser.class);
        Optional<DBUser> dbUserByEmail = dbUserRepository.findByEmail(user.getEmail());
        Optional<DBUser> dbUserByUsername = dbUserRepository.findByUsername(user.getUsername());
        if(dbUserByEmail.isPresent() || dbUserByUsername.isPresent()) {
            throw new EntityExistsException("Un utilisateur avec cet e-mail ou nom d'utilisateur existe déjà.");
        }
        Timestamp formattedDate = DateUtils.now();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(formattedDate);
        user.setUpdatedAt(formattedDate);
        dbUserRepository.save(user);
        return new ResponseDTO("Compte crée avec succès");
    }

    @Override
    public DBUserDTO findByEmail(final String email) throws UsernameNotFoundException {
        DBUser dbUser = dbUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return modelMapper.map(dbUser, DBUserDTO.class);
    }

    @Override
    public TokenDTO login(final DBUserDTO user) throws UsernameNotFoundException {
        String login = user.getEmail();
        Optional<DBUser> dbUser = dbUserRepository.findByUsername(login);
        if(checkIsEmail(user.getEmail())){
            dbUser = dbUserRepository.findByEmail(login);
        }
        if(dbUser.isPresent()) {
            String password = user.getPassword();
            if(!passwordEncoder.matches(password, dbUser.get().getPassword())){
                throw new BadCredentialsException("Mot de passe incorrect");
            }
            return new TokenDTO(jwtService.generateToken(dbUser.get().getEmail()));
        }
        else{
            throw new UsernameNotFoundException("Utilisateur introuvable");
        }
    }

    @Override
    public TokenDTO update(final DBUserDTO updatedUser, final Principal loggedUser) throws UsernameNotFoundException {
        DBUser current = dbUserRepository.findByEmail(loggedUser.getName()).orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));

        Optional<DBUser> newEmailAlreadyExists = dbUserRepository.findByEmail(updatedUser.getEmail());
        Optional<DBUser> newUsernameAlreadyExists = dbUserRepository.findByUsername(updatedUser.getUsername());
        // If the email has changed, we check that the chosen email doesn't exists
        if(!current.getEmail().equals(updatedUser.getEmail())){
            if(newEmailAlreadyExists.isPresent()){
                throw new UsernameNotFoundException("le nouvel e-mail choisi est déjà utilisé.");
            }
        }
        // If the username has changed, we check that the chosen username doesn't exists
        else if(!current.getUsername().equals(updatedUser.getUsername())){
            if(newUsernameAlreadyExists.isPresent()){
                throw new UsernameNotFoundException("le nouveau nom d'utilisateur choisi est déjà utilisé.");
            }
        }

        current.setUsername(updatedUser.getUsername());
        current.setEmail(updatedUser.getEmail());
        current.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        current.setUpdatedAt(DateUtils.now());
        dbUserRepository.save(current);

        String newToken = jwtService.generateToken(updatedUser.getEmail());
        return new TokenDTO(newToken);

    }

    /**
     * Loads the user's data given the user's email.
     *
     * @param email The email of the user to load.
     * @return UserDetails containing the user's information if the user is found.
     * @throws UsernameNotFoundException if the user cannot be found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        DBUser user = dbUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(format("User: %s not found in db", email)));
        return new User(user.getEmail(), user.getPassword(), getGrantedAuthorities("USER"));
    }

    public boolean checkPassword(DBUserDTO user){
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};:\"\\\\|,.<>\\/?]).{8,}$");
        return pattern.matcher(user.getPassword()).matches();
    }

    public boolean checkIsEmail(String usernameOrEmail){
        return usernameOrEmail.contains("@");
    }

    /**
     * Assigns a default role of "USER" to the authenticated user.
     *
     * @param role The role to be granted to the user. This is a placeholder and currently only supports "USER".
     * @return A list of GrantedAuthority based on the roles assigned.
     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}
