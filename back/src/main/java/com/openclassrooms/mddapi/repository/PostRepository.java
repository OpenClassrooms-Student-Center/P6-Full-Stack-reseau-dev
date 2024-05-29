package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    List<Post> findPostsByTopic_UsersId(Long userId);
}
