package com.investment_qna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment_qna.DTO.QuestionDTO;
import com.investment_qna.model.Category;
import com.investment_qna.model.Question;
import com.investment_qna.model.SubCategory;
import com.investment_qna.repository.CategoryRepository;
import com.investment_qna.repository.QuestionRepository;
import com.investment_qna.repository.SubCategoryRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/questions")
@Tag(name = "Questions", description = "APIs for managing questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    // Get all questions
    @GetMapping
    @Operation(summary = "Get all questions", description = "Retrieve all questions")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    
    @GetMapping("/by-subcategory/{subCategoryId}")
    @Operation(summary = "Get questions by subcategory", description = "Retrieve all questions under a specific subcategory")
    public List<QuestionDTO> getQuestionsBySubCategory(@PathVariable Long subCategoryId) {
        
        List<Question> questions = questionRepository.findBySubCategoryId(subCategoryId);

        // Map to DTO to avoid infinite recursion
        return questions.stream().map(q -> {
            QuestionDTO dto = new QuestionDTO();
            dto.setId(q.getId());
            dto.setQuestionText(q.getQuestionText());
            dto.setCategoryId(q.getCategory().getId());
            dto.setCategoryName(q.getCategory().getName());
            dto.setSubCategoryId(q.getSubCategory().getId());
            dto.setSubCategoryName(q.getSubCategory().getName());
            return dto;
        }).toList();
    }

    @GetMapping("/by-category/{categoryId}")
    @Operation(summary = "Get questions by subcategory", description = "Retrieve all questions under a specific subcategory")
    public List<QuestionDTO> getQuestionsByCategory(@PathVariable Long categoryId) {
        
        List<Question> questions = questionRepository.findByCategoryId(categoryId);

        // Map to DTO to avoid infinite recursion
        return questions.stream().map(q -> {
            QuestionDTO dto = new QuestionDTO();
            dto.setId(q.getId());
            dto.setQuestionText(q.getQuestionText());
            dto.setCategoryId(q.getCategory().getId());
            dto.setCategoryName(q.getCategory().getName());
            dto.setSubCategoryId(q.getSubCategory().getId());
            dto.setSubCategoryName(q.getSubCategory().getName());
            return dto;
        }).toList();
    }


    // Get single question by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get question by ID", description = "Retrieve a single question by its ID")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        return questionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create question", description = "Add a new question under category and subcategory")
    public Question createQuestion(@RequestBody QuestionDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        SubCategory subCategory = subCategoryRepository.findById(dto.getSubCategoryId())
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        Question question = new Question();
        question.setQuestionText(dto.getQuestionText());
        question.setCategory(category);
        question.setSubCategory(subCategory);

        return questionRepository.save(question);
    }



    // Update question
    @PutMapping("/{id}")
    @Operation(summary = "Update question", description = "Modify an existing question")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question updatedQuestion) {
        return questionRepository.findById(id).map(question -> {
            question.setQuestionText(updatedQuestion.getQuestionText());
            question.setCategory(updatedQuestion.getCategory());
            questionRepository.save(question);
            return ResponseEntity.ok(question);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete question
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete question", description = "Remove a question by ID")
    public ResponseEntity<Object> deleteQuestion(@PathVariable Long id) {
        return questionRepository.findById(id).map(question -> {
            questionRepository.delete(question);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}


