package com.investment_qna.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment_qna.DTO.CategoryDTO;
import com.investment_qna.DTO.QuestionDTO;
import com.investment_qna.model.Category;
import com.investment_qna.model.Question;
import com.investment_qna.repository.CategoryRepository;
import com.investment_qna.repository.QuestionRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Categories", description = "APIs for managing categories and related questions")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // Create category
    @PostMapping
    @Operation(summary = "Create category", description = "Add a new category")
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    // Get all categories
    @GetMapping
    @Operation(summary = "Get all categories", description = "Fetch all categories with their questions")
    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(cat -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(cat.getId());
            dto.setName(cat.getName());
            dto.setDescription(cat.getDescription());

//            List<QuestionDTO> questions = cat.getQuestions().stream().map(q -> {
//                QuestionDTO qdto = new QuestionDTO();
//                qdto.setId(q.getId());
//                qdto.setQuestionText(q.getQuestionText());
//                return qdto;
//            }).toList();

//            dto.setQuestions(questions);
            return dto;
        }).toList();
    }

    @GetMapping("/{id}/questions")
    @Operation(summary = "Get questions by category", description = "Fetch all questions for a given category")
    public List<QuestionDTO> getQuestionsByCategory(@PathVariable Long id) {
        List<Question> questions = questionRepository.findByCategoryId(id);

        return questions.stream().map(q -> {
            QuestionDTO dto = new QuestionDTO();
            dto.setId(q.getId());
            dto.setQuestionText(q.getQuestionText());
            return dto;
        }).toList();
    }
}

