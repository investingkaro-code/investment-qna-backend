package com.investment_qna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investment_qna.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
