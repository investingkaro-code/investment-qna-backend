package com.investment_qna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investment_qna.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	List<Answer> findByUserId(Long userId);

	List<Answer> findByUserIdAndQuestionCategoryId(Long userId, Long categoryId);

	// Answers by user + subcategory
	List<Answer> findByUserIdAndQuestionSubCategoryId(Long userId, Long subCategoryId);

	List<Answer> findByUserIdAndStockSymbolAndQuestionCategoryId(Long userId, String stockSymbol, Long categoryId);

	long countByUserIdAndStockSymbolAndQuestionCategoryId(Long userId, String stockSymbol, Long categoryId);

	void deleteByUserIdAndStockSymbol(Long userId, String stockSymbol);

}
