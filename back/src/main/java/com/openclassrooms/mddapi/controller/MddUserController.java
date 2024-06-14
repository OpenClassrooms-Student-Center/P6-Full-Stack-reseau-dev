package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dtos.MddUserDto;
import com.openclassrooms.mddapi.dtos.requests.NewPasswordRequest;
import com.openclassrooms.mddapi.dtos.requests.NewUserInfoRequest;
import com.openclassrooms.mddapi.dtos.responses.MessageResponse;
import com.openclassrooms.mddapi.dtos.responses.UserInfoResponse;
import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.mappers.MddUserMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.MddUserService;
import com.openclassrooms.mddapi.service.TopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * API Controller for user management: includes operations for fetching, creating, updating and deleting users.
 *
 */
@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("users/user")
@SecurityRequirement(name = "Authorization")
public class MddUserController {

    @Autowired
    MddUserService mddUserService;

    @Autowired
    private MddUserMapper mddUserMapper;

    @Autowired
    TopicService topicService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Returns all the users present in the system
     *
     * @return List of all users
     */
    @GetMapping("/users")
    public ResponseEntity<List<MddUserDto>> getUsers() {
        return ResponseEntity.ok(mddUserMapper.toDto(mddUserService.findAllUsers()));
    }

    /**
     * Returns the user who matches the provided Id
     *
     * @param id - User's unique id
     * @return UserDto object of the fetched user
     */
    @GetMapping("/{id}")
    public ResponseEntity<MddUserDto> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mddUserMapper.toDto(mddUserService.findUserById(id)));
    }

    /**
     * Creates a new user
     *
     * @param mddUserDto - Data transfer object containing details of the user
     * @return DTO of the User created
     */
    @PostMapping("/create")
    public ResponseEntity<MddUserDto> create(@RequestBody MddUserDto mddUserDto) {
        return ResponseEntity.ok(mddUserMapper.toDto(mddUserService.createUser(mddUserMapper.toEntity(mddUserDto))));
    }

    /**
     * Updates the details of an existing user
     *
     * @param mddUserDto - Data transfer object containing updated details of the user
     * @return DTO of the User updated
     */
    @PutMapping("/update")
    public ResponseEntity<MddUserDto> update(@RequestBody MddUserDto mddUserDto) {
        return ResponseEntity.ok(mddUserMapper.toDto(mddUserService.updateUser(mddUserMapper.toEntity(mddUserDto))));
    }

    /**
     * Deletes a user
     *
     * @param id - Id of the user to be deleted
     * @return Message response of the operation performed
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new MessageResponse(mddUserService.deleteUser(id)));
    }

    /**
     * Fetches details of the logged in user
     *
     * @param authentication - Authentication object containing details of the logged-in user
     * @return User's details
     */
    @GetMapping("/contactinfo")
    public ResponseEntity<UserInfoResponse> me(Authentication authentication){
        MddUser mddUser = mddUserService.findUserByEmail(authentication.getName());
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setUsername(mddUser.getUsername());
        userInfoResponse.setEmail(mddUser.getEmail());
        userInfoResponse.setTopicsIds(topicService.mySubscriptions(mddUser));
        log.info("Call for myInfo");
        System.out.println("Me info request for :" + authentication + userInfoResponse);
        return ResponseEntity.ok(userInfoResponse);
    }

    /**
     * Updates the logged in user's information
     *
     * @param newUserInfoRequest - Contains the updated information of the user
     * @param authentication - Authentication object containing details of the logged-in user
     * @return Message response of the operation performed
     */
    @PutMapping("newinfo")
    public ResponseEntity<MessageResponse> newInfo(@RequestBody NewUserInfoRequest newUserInfoRequest, Authentication authentication){
        MddUser mddUser = mddUserService.findUserByEmail(authentication.getName());
        mddUser.setUsername(newUserInfoRequest.getUsername());
        mddUser.setEmail(newUserInfoRequest.getEmail());
        mddUserService.updateUser(mddUser);
        return ResponseEntity.ok(new MessageResponse("User information updated successfully"));
    }

    /**
     * Updates the logged in user's password
     *
     * @param newPasswordRequest - Contains the new password of the user
     * @param authentication - Authentication object containing details of the logged-in user
     * @return Message response of the operation performed
     */
    @PutMapping("newpass")
    public ResponseEntity<MessageResponse> newPassword(@RequestBody NewPasswordRequest newPasswordRequest, Authentication authentication){
        MddUser mddUser = mddUserService.findUserByEmail(authentication.getName());
        mddUser.setPassword(passwordEncoder.encode(newPasswordRequest.getNewPass()));
        mddUserService.updateUser(mddUser);
        return ResponseEntity.ok(new MessageResponse("Password updated successfully"));
    }
}