package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.model.Themes;

public interface ThemesRepository extends JpaRepository<Themes, Long> {

}
