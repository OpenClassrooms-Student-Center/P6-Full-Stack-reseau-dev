package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.model.Messages;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

}
