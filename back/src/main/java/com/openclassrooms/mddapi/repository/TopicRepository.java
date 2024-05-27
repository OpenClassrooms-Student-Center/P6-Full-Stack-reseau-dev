package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Topic;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {


    List<Topic> findTopicsByUsersId(Long id);

}