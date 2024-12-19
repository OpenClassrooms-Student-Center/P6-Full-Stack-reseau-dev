package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    public List<Comment> findByPostId(Long postId);

}
