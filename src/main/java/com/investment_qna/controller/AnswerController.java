package com.investment_qna.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.investment_qna.DTO.AnswerDTO;
import com.investment_qna.DTO.BulkAnswerRequestDTO;
import com.investment_qna.model.Answer;
import com.investment_qna.model.Question;
import com.investment_qna.model.User;
import com.investment_qna.repository.AnswerRepository;
import com.investment_qna.repository.QuestionRepository;
import com.investment_qna.repository.UserRepository;
import com.investment_qna.service.AnswerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/answers")
@Tag(name = "Answers", description = "APIs for submitting and retrieving user answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;
    
    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private AnswerRepository answerRepository;
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Submit answer", description = "Save a user's answer for a specific question")
    public Answer submitAnswer(@RequestBody AnswerDTO dto, Principal principal) {
        // 1️⃣ Fetch the Question
        Question question = questionRepository.findById(dto.getQuestionId())
            .orElseThrow(() -> new RuntimeException("Question not found"));

        // 2️⃣ Fetch the User from Principal
        User user = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // 3️⃣ Create and save Answer
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setAnswerText(dto.getAnswerText());
        answer.setUser(user);

        return answerRepository.save(answer);
    }



    @GetMapping
    @Operation(summary = "Get user answers", description = "Fetch all answers submitted by a specific user")
    public List<AnswerDTO> getUserAnswers(@RequestParam Long userId) {
        return answerRepository.findByUserId(userId)
            .stream()
            .map(answer -> new AnswerDTO(answer))
            .collect(Collectors.toList());
    }
    
 // ✅ Get answers by Category
    @GetMapping("/by-category")
    @Operation(summary = "Get user answers by category", description = "Fetch answers submitted by a user for a specific category")
    public List<AnswerDTO> getAnswersByCategory(@RequestParam Long userId, @RequestParam Long categoryId) {
        return answerRepository.findByUserIdAndQuestionCategoryId(userId, categoryId)
                .stream()
                .map(answer -> new AnswerDTO(answer))
                .collect(Collectors.toList());
    }

    // ✅ Get answers by SubCategory
    @GetMapping("/by-subcategory")
    @Operation(summary = "Get user answers by subcategory", description = "Fetch answers submitted by a user for a specific subcategory")
    public List<AnswerDTO> getAnswersBySubCategory(@RequestParam Long userId, @RequestParam Long subCategoryId) {
        return answerRepository.findByUserIdAndQuestionSubCategoryId(userId, subCategoryId)
                .stream()
                .map(answer -> new AnswerDTO(answer))
                .collect(Collectors.toList());
    }
    
    @PostMapping("/bulk")
    @Operation(
        summary = "Submit bulk answers",
        description = "Save multiple answers for a stock in one request"
    )
    public void submitBulkAnswers(
            @RequestBody BulkAnswerRequestDTO request,
            Principal principal
    ) {
        // Fetch user from Principal
        User user = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

        answerService.saveBulkAnswers(request, user);
    }



}
