package com.openclassrooms.p6.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.p6.model.Themes;

public interface ThemeRepository extends JpaRepository<Themes, Long> {

}
