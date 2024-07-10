package com.openclassrooms.mddapi.service.user;

import jakarta.persistence.EntityExistsException;

import com.openclassrooms.mddapi.dto.DBUserDTO;
import com.openclassrooms.mddapi.model.DBUser;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import com.openclassrooms.mddapi.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class DBUserService implements IDBUserService {

    @Autowired
    private DBUserRepository dbUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DateUtils DateUtils;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void create(final DBUserDTO userDTO) throws EntityExistsException{

        DBUser user = modelMapper.map(userDTO, DBUser.class);

        Optional<DBUser> dbUserByEmail = dbUserRepository.findByEmail(user.getEmail());
        Optional<DBUser> dbUserByUsername = dbUserRepository.findByUsername(user.getUsername());
        if(dbUserByEmail.isPresent() || dbUserByUsername.isPresent()) {
            throw new EntityExistsException("User already exists in DB");
        }

        Timestamp formattedDate = DateUtils.now();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(formattedDate);
        user.setUpdatedAt(formattedDate);

        dbUserRepository.save(user);
    }

    @Override
    public DBUserDTO findByEmail(final String userEmail) throws UsernameNotFoundException {

        Optional<DBUser> dbUser = dbUserRepository.findByEmail(userEmail);

        if(dbUser.isPresent()) {
            return modelMapper.map(dbUser.get(), DBUserDTO.class);
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }

    }

    @Override
    public DBUserDTO findByUsername(final String username) throws UsernameNotFoundException {

        Optional<DBUser> dbUser = dbUserRepository.findByUsername(username);

        if(dbUser.isPresent()) {
            return modelMapper.map(dbUser.get(), DBUserDTO.class);
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }

    }

    @Override
    public DBUserDTO findById(final Integer userId) throws UsernameNotFoundException {

        Optional<DBUser> dbUser = dbUserRepository.findById(userId);

        if(dbUser.isPresent()) {
            return modelMapper.map(dbUser.get(), DBUserDTO.class);
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }

    }

    @Override
    public void update(final DBUserDTO updatedUser, final Principal loggedUser) throws UsernameNotFoundException {

        Optional<DBUser> emailExist = dbUserRepository.findByEmail(updatedUser.getEmail());
        if(emailExist.isPresent()) {
            throw new EntityExistsException("Email already exists in DB");
        }

        Optional<DBUser> usernameExist = dbUserRepository.findByUsername(updatedUser.getUsername());
        if(usernameExist.isPresent()) {
            throw new EntityExistsException("Username already exists in DB");
        }

        Optional<DBUser> dbUser = dbUserRepository.findByEmail(loggedUser.getName());

        if(dbUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        DBUser user = dbUser.get();
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        user.setUpdatedAt(DateUtils.now());
        dbUserRepository.save(user);
        modelMapper.map(user, DBUserDTO.class);

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

        Optional<DBUser> user = dbUserRepository.findByEmail(email);

        if(user.isPresent()) {
            DBUser existingUser = user.get();

            return new User(existingUser.getEmail(), existingUser.getPassword(), getGrantedAuthorities("USER"));
        }
        else {
            throw new UsernameNotFoundException(format("User: %s not found in db", email));
        }

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
