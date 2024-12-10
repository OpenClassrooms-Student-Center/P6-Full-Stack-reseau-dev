package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.ThemeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<ThemeModel, Long> {
}
