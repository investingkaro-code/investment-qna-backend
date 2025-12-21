package com.investment_qna.DTO;

import java.util.List;

public class BulkAnswerRequestDTO {

    private String stockSymbol;   // 🔥 heading
    private List<AnswerDTO> answers;

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }
}
