package com.investment_qna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investment_qna.model.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategoryId(Long categoryId);
}

