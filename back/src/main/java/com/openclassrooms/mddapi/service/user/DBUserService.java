package com.openclassrooms.mddapi.service.user;

import jakarta.persistence.EntityExistsException;

import com.openclassrooms.mddapi.dto.DBUserDTO;
import com.openclassrooms.mddapi.model.DBUser;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import com.openclassrooms.mddapi.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Optional;

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

        Optional<DBUser> dbUser = dbUserRepository.findByEmail(user.getEmail());

        if(dbUser.isPresent()) {
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
}
