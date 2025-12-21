package com.investment_qna.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.investment_qna.DTO.AnswerDTO;
import com.investment_qna.DTO.BulkAnswerRequestDTO;
import com.investment_qna.DTO.ResumeAnswerDTO;
import com.investment_qna.DTO.ResumeResponseDTO;
import com.investment_qna.model.Answer;
import com.investment_qna.model.Question;
import com.investment_qna.model.User;
import com.investment_qna.repository.AnswerRepository;
import com.investment_qna.repository.QuestionRepository;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;
    
    @Autowired
    private QuestionRepository questionRepository;


    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public List<Answer> getUserAnswers(Long userId) {
        return answerRepository.findByUserId(userId);
    }
    
    @Transactional
    public void saveBulkAnswers(
            BulkAnswerRequestDTO request,
            User user
    ) {

        List<Answer> answersToSave = new ArrayList<>();

        for (AnswerDTO dto : request.getAnswers()) {

            Question question = questionRepository.findById(dto.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

            Answer answer = new Answer();
            answer.setUser(user);
            answer.setQuestion(question);
            answer.setAnswerText(dto.getAnswerText());
            answer.setStockSymbol(request.getStockSymbol()); // 🔥

            answersToSave.add(answer);
        }

        answerRepository.saveAll(answersToSave);
    }
    
    public ResumeResponseDTO getResumeAnswers(
            Long userId,
            String stockSymbol,
            Long categoryId
    ) {
        List<Answer> answers =
            answerRepository.findByUserIdAndStockSymbolAndQuestionCategoryId(
                userId, stockSymbol, categoryId
            );

        long answeredCount = answers.size();

        long totalQuestions =
            questionRepository.countByCategoryId(categoryId);

        List<ResumeAnswerDTO> answerDTOs = answers.stream()
            .map(a -> new ResumeAnswerDTO(
                a.getQuestion().getId(),
                a.getAnswerText()
            ))
            .toList();

        ResumeResponseDTO response = new ResumeResponseDTO();
        response.setAnsweredCount(answeredCount);
        response.setTotalQuestions(totalQuestions);
        response.setAnswers(answerDTOs);

        return response;
    }

}
