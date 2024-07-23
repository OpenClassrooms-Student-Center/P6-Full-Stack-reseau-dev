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
	
	public CommentService(CommentRepository commentRepository, DBUserRepository dbUserRepository) {
        this.commentRepository = commentRepository;
        this.dbUserRepository = dbUserRepository;
    }

	@Override
	public List<CommentDTO> getCommentsByPost(Long postId) {
		return commentRepository.findByPostId(postId).stream()
				.map(entity -> modelMapper.map(entity, CommentDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public ResponseDTO createComment(CommentDTO commentDTO, Principal user){
		DBUser dbUser = dbUserRepository.findByEmail(user.getName()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		if(!dbUser.getId().equals(commentDTO.getUserId())){
			throw new EntityNotFoundException("L'utilisateur sélectionné est différent de l'utilisateur connecté");
		}
		Timestamp now = DateUtils.now();
		Comment newComment = modelMapper.map(commentDTO, Comment.class);
		newComment.setCreatedAt(now);
		newComment.setUserOwner(dbUser);
		commentRepository.save(newComment);
		return new ResponseDTO("Comment created !");
	}

}
