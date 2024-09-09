package com.openclassrooms.p6.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.p6.model.Users;
import com.openclassrooms.p6.payload.request.RegisterRequest;
import com.openclassrooms.p6.repository.UserRepository;

import lombok.Data;

/**
 * Service class for managing users.
 */
@Data
@Service
public class UserService {

    /**
     * 
     * Usser repo to perform database operations on the {@link Users} entity.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * 
     * Dependency to encode passwords
     */
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Retrieve a user by their unique identifier.
     *
     * @param id The identifier of the user.
     * @return An Optional containing the user if found, or empty if not.
     */
    public Optional<Users> getUserById(final Long id) {
        return userRepository.findById(id);
    }

    /**
     * Retrieve all users.
     *
     * @return Iterable collection of all users.
     */
    public Iterable<Users> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Save or update a user.
     *
     * @param user The user to be saved or updated.
     * @return The saved or updated user.
     */
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    /**
     * Delete a user by their unique identifier.
     *
     * @param id The identifier of the user to be deleted.
     */
    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Retrieve a user by their username.
     *
     * @param username The username of the user.
     * @return An Optional containing the user if found, or empty if not.
     */
    public Optional<Users> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Verify if the provided username is already in use.
     *
     * @param username The username to be verified.
     * @return True if the username is already in use, false otherwise.
     */
    public boolean isUsernameInUse(String username) {
        Optional<Users> existingUser = getUserByUsername(username);

        return existingUser.isPresent();
    }

    /**
     * Retrieve a user by their email address.
     *
     * @param email The email address of the user.
     * @return An Optional containing the user if found, or empty if not.
     */
    public Optional<Users> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Verify if the provided email is already in use.
     *
     * @param email The email to be verified.
     * @return True if the email is already in use, false otherwise.
     */
    public boolean isEmailInUse(String email) {
        Optional<Users> existingUser = getUserByEmail(email);

        return existingUser.isPresent();
    }

    /**
     * Verify if the provided password matches the stored hashed password.
     *
     * @param user     The user for which to verify the password.
     * @param password The password to be verified.
     * @return True if the password matches, false otherwise.
     */
    public boolean isPasswordValid(String password, Users user) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    /**
     * Save or update a user using information from the registration request.
     *
     * @param registrationRequest The registration request containing user details.
     * @return The saved or updated user.
     */
    public Users saveUserBySignUp(RegisterRequest registrationRequest) {
        Users user = new Users();

        LocalDateTime currentTime = LocalDateTime.now();

        String encodedPassword = passwordEncoder.encode(registrationRequest.password());

        user.setUsername(registrationRequest.username());
        user.setEmail(registrationRequest.email());
        user.setPassword(encodedPassword);
        user.setCreatedAt(currentTime);
        user.setUpdatedAt(currentTime);

        return saveUser(user);
    }

}
