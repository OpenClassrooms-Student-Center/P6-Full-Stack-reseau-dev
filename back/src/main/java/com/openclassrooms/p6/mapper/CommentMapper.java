package com.openclassrooms.p6.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.p6.model.Comments;
import com.openclassrooms.p6.payload.response.CommentResponse;

/**
 * Interface responsible for mapping between {@link Comments} domain models and
 * {@link CommentResponse} data transfer objects.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {

    /**
     * Map a {@link Comments} object to its respective {@link CommentResponse} DTO
     * representation.
     *
     * @param comment The source {@link Comments} object.
     * @return The destination {@link CommentResponse} DTO object.
     */
    @Mapping(target = "username", source = "user.username")
    CommentResponse toDtoComment(Comments comment);

    /**
     * Map a list of {@link Comments} objects to a list of {@link CommentResponse}
     * DTO representations.
     *
     * @param comments The source list of {@link Comments} objects.
     * @return The destination list of {@link CommentResponse} DTO objects.
     */
    Iterable<CommentResponse> toDtoComments(List<Comments> comments);
}