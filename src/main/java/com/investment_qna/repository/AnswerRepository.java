package com.investment_qna.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investment_qna.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByUserId(Long userId);
}
