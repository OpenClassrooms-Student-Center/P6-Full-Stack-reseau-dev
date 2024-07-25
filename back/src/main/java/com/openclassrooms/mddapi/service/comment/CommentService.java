package com.openclassrooms.mddapi.service.comment;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.DBUser;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.util.DateUtils;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DateUtils DateUtils;

	private final CommentRepository commentRepository;
	private final DBUserRepository dbUserRepository;
	private final PostRepository postRepository;
	
	public CommentService(CommentRepository commentRepository, DBUserRepository dbUserRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.dbUserRepository = dbUserRepository;
        this.postRepository = postRepository;
    }

	@Override
	public List<CommentDTO> getCommentsByPost(Long postId) {
		return commentRepository.findByPostId(postId).stream()
				.map(entity -> modelMapper.map(entity, CommentDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<CommentDTO> createComment(CommentDTO commentDTO, Long postId, Principal user){
		DBUser dbUser = dbUserRepository.findByEmail(user.getName()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
		Timestamp now = DateUtils.now();
		Comment newComment = modelMapper.map(commentDTO, Comment.class);
		newComment.setCreatedAt(now);
		newComment.setPost(post);
		newComment.setUser(dbUser);
		commentRepository.save(newComment);
		return this.getCommentsByPost(postId);
	}

}
