package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.MddUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MddUserRepository  extends JpaRepository<MddUser, Long> {
    Optional<MddUser> findByUsername(String username);

    Optional<MddUser> findByEmail(String username);
}
