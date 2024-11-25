package com.spring.blogs.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.blogs.models.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
	Optional<CategoryEntity> findByTitle(String title);
}
