package com.openclassrooms.mddapi.service.user;

import jakarta.persistence.EntityExistsException;

import com.openclassrooms.mddapi.dto.DBUserDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;

public interface IDBUserService {
    void create(DBUserDTO userDTO) throws EntityExistsException;
    DBUserDTO findByEmail(String userEmail) throws UsernameNotFoundException;
    DBUserDTO findByUsername(String username) throws UsernameNotFoundException;
    DBUserDTO findById(Integer userId) throws UsernameNotFoundException;
    void update(DBUserDTO updatedUser, Principal loggedUser) throws UsernameNotFoundException;

}
