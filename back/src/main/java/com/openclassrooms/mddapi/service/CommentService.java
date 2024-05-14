package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.MddUser;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class CommentService {

    CommentRepository commentRepository;

    public List<Comment> findAllComments() {
        try {
            log.info("findAllComments");
            List<Comment> commentList = new ArrayList<>();
            commentRepository.findAll().forEach(ct -> commentList.add(ct));
            return commentList;
        } catch (Exception e) {
            log.error("We could not find all comments: " + e.getMessage());
            throw new RuntimeException("We could not find any comments");
        }
    }

    public Comment findCommentById(Long id) {
        try {
            log.info("findCommentById - id: " + id);
            Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
            return comment;
        } catch (Exception e) {
            log.error("We could not find comment: " + id, e.getMessage());
            throw new RuntimeException("We could not find your comment");
        }
    }

    public List<Comment> findAllByIds(List<Long> ids){
        try {
            log.info("findAllByIds - ids: " + ids);
            List<Comment> comments = commentRepository.findAllById(ids);
            return comments;
        } catch (Exception e) {
            log.error("We could not find comments: " + ids, e.getMessage());
            throw new RuntimeException("We could not find your comments");
        }
    }
    public Comment createComment(Comment comment) {
        try {
            log.info("createComment");
            comment.setId(null);
            comment = commentRepository.save(comment);
            return comment;
        } catch (Exception e) {
            log.error("Failed to create comment: ", e.getMessage());
            throw new RuntimeException("Failed to create comment");
        }
    }

    public Comment updateComment(Comment comment) {
        try {
            log.info("updateComment - id: " + comment.getId());
            Comment existingComment = commentRepository.findById(comment.getId())
                    .orElseThrow(() -> new RuntimeException("Comment not found"));
            existingComment.setPost(comment.getPost());
            existingComment.setAuthor(comment.getAuthor());
            commentRepository.save(existingComment);
            return existingComment;
        } catch (Exception e) {
            log.error("Failed to update comment: ", e.getMessage());
            throw new RuntimeException("Failed to update comment");
        }
    }

    public String deleteComment(Long id) {
        try {
            log.info("deleteComment - id: " + id);
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Comment not found"));
            commentRepository.delete(comment);
            return "Comment deleted";
        } catch (Exception e) {
            log.error("Failed to delete comment: ", e.getMessage());
            throw new RuntimeException("Failed to delete comment");
        }
    }
}