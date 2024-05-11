package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.repository.MddUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@Transactional
public class MddUserService {

    MddUserRepository mddUserRepository;

    public List<MddUser> findAllUsers() {
        try {
            log.info("findAllUsers");
            List<MddUser> userList = new ArrayList<>();
            mddUserRepository.findAll().forEach(user -> userList.add(user));
            return userList;
        } catch (Exception e) {
            log.error("We could not find all users: " + e.getMessage());
            throw new RuntimeException("We could not find any users");
        }
    }



    public MddUser findUserById(Long id) {
        try {
            log.info("findUserById - id: " + id);
            return mddUserRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            log.error("We could not find user: " + id, e.getMessage());
            throw new RuntimeException("We could not find your user");
        }
    }

    public MddUser createUser(MddUser user) {
        try {
            log.info("createUser");
            user.setId(null);
            return mddUserRepository.save(user);
        } catch (Exception e) {
            log.error("Failed to create user: ", e.getMessage());
            throw new RuntimeException("Failed to create user");
        }
    }

    public MddUser updateUser(MddUser user) {
        try {
            log.info("updateUser - id: " + user.getId());
            MddUser existingUser = mddUserRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setComments(user.getComments());
            existingUser.setPosts(user.getPosts());

            mddUserRepository.save(existingUser);
            return existingUser;
        } catch (Exception e) {
            log.error("Failed to update user: ", e.getMessage());
            throw new RuntimeException("Failed to update user");
        }
    }

    public String deleteUser(Long id) {
        try {
            log.info("deleteUser - id: " + id);
            MddUser user = mddUserRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            mddUserRepository.delete(user);
            return "User deleted";
        } catch (Exception e) {
            log.error("Failed to delete user: ", e.getMessage());
            throw new RuntimeException("Failed to delete user");
        }
    }
}