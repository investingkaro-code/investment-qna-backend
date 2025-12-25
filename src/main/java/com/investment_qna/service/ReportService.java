package com.investment_qna.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.investment_qna.DTO.QuestionAnswerDTO;
import com.investment_qna.DTO.StockReportDTO;
import com.investment_qna.DTO.SubCategoryReportDTO;
import com.investment_qna.model.Answer;
import com.investment_qna.repository.AnswerRepository;

@Service
public class ReportService {

    @Autowired
    private AnswerRepository answerRepository;

    public List<StockReportDTO> getUserReport(Long userId) {

        List<Answer> answers = answerRepository.findByUserId(userId);

        Map<String, Map<String, List<Answer>>> grouped =
            answers.stream().collect(
                Collectors.groupingBy(
                    Answer::getStockSymbol,
                    Collectors.groupingBy(
                        a -> a.getQuestion().getSubCategory().getName()
                    )
                )
            );

        List<StockReportDTO> report = new ArrayList<>();

        for (var stockEntry : grouped.entrySet()) {
            StockReportDTO stockDto = new StockReportDTO();
            stockDto.setStockSymbol(stockEntry.getKey());

            List<SubCategoryReportDTO> subList = new ArrayList<>();

            for (var subEntry : stockEntry.getValue().entrySet()) {
                SubCategoryReportDTO subDto = new SubCategoryReportDTO();
                subDto.setSubCategoryName(subEntry.getKey());

                List<QuestionAnswerDTO> qaList = subEntry.getValue().stream()
                    .map(a -> {
                        QuestionAnswerDTO qa = new QuestionAnswerDTO();
                        qa.setQuestionText(a.getQuestion().getQuestionText());
                        qa.setAnswerText(a.getAnswerText());
                        return qa;
                    })
                    .toList();

                subDto.setQuestions(qaList);
                subList.add(subDto);
            }

            stockDto.setSubcategories(subList);
            report.add(stockDto);
        }

        return report;
    }
    
	@Transactional
	public void deleteReport(Long userId, String stockSymbol) {
		answerRepository.deleteByUserIdAndStockSymbol(userId, stockSymbol);
	}


}
