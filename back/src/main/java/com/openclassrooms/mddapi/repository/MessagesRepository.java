package com.openclassrooms.mddapi.repository;
import com.openclassrooms.mddapi.model.Messages;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Messages, Long>{
    
}
