package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.MddUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MddUserRepository  extends JpaRepository<MddUser, Long> {
}
