package com.openclassrooms.mddapi.service.user;

import jakarta.persistence.EntityExistsException;

import com.openclassrooms.mddapi.dto.DBUserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;

public interface IDBUserService extends UserDetailsService {
    /**
     * Creates a new user in the database.
     *
     * @param userDTO the user data transfer object containing the user's information
     * @throws EntityExistsException if a user with the same email or username already exists
     */
    void create(final DBUserDTO userDTO) throws EntityExistsException;
    /**
     * Finds a user by their email.
     *
     * @param userEmail the email of the user to find
     * @return the found user as a data transfer object
     * @throws UsernameNotFoundException if no user with the given email can be found
     */
    DBUserDTO findByEmail(final String userEmail) throws UsernameNotFoundException;
    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return the found user as a data transfer object
     * @throws UsernameNotFoundException if no user with the given username can be found
     */
    DBUserDTO findByUsername(final String username) throws UsernameNotFoundException;
    /**
     * Finds a user by their ID.
     *
     * @param userId the ID of the user to find
     * @return the found user as a data transfer object
     * @throws UsernameNotFoundException if no user with the given ID can be found
     */
    DBUserDTO findById(final Integer userId) throws UsernameNotFoundException;
    /**
     * Updates a user's information.
     *
     * @param updatedUser the user data transfer object containing the updated information
     * @param loggedUser the principal of the currently logged-in user
     * @throws UsernameNotFoundException if the user to update cannot be found
     */
    void update(final DBUserDTO updatedUser, final Principal loggedUser) throws UsernameNotFoundException;

}
