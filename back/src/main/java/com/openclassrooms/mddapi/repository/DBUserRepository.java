package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DBUserRepository extends JpaRepository<DBUser, Integer> {
    public Optional<DBUser> findByEmail(String email);
    public Optional<DBUser> findByUsername(String username);
}
