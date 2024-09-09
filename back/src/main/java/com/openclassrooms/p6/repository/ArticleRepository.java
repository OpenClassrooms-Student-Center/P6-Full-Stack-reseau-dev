package com.openclassrooms.p6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.p6.model.Articles;

/**
 * Repository interface for managing Article entities in the database.
 */
@Repository
public interface ArticleRepository extends JpaRepository<Articles, Long> {

}
