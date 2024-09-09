package com.openclassrooms.p6.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.openclassrooms.p6.model.Articles;
import com.openclassrooms.p6.payload.response.ArticleSummaryResponse;

/**
 * Interface defining methods for mapping between {@link Articles} and
 * {@link ArticleSummaryResponse}.
 */
@Mapper(componentModel = "spring")
public interface ArticleMapper {

    /**
     * Maps an {@link Articles} object to a {@link ArticleSummaryResponse} object.
     * <p>
     * Fields copied:
     * <ul>
     * <li>{@code id} -> {@code articleId}</li>
     * <li>{@code publicationDate} -> {@code createdAt}</li>
     * </ul>
     * Other fields ignored during copy.
     *
     * @param article The {@link Articles} object to be converted.
     * @return The corresponding {@link ArticleSummaryResponse} object.
     */
    @Mappings({
            @Mapping(target = "id", ignore = false),
            @Mapping(target = "publicationDate", source = "createdAt"),
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "articleId", source = "id")
    })
    ArticleSummaryResponse toDtoArticle(Articles article);

    /**
     * Maps a list of {@link Articles} objects to a list of
     * {@link ArticleSummaryResponse} objects.
     *
     * @param articles The {@link Articles} objects to be converted.
     * @return The corresponding list of {@link ArticleSummaryResponse} objects.
     */
    Iterable<ArticleSummaryResponse> toDtoArticles(List<Articles> articles);
}