package com.investment_qna.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment_qna.DTO.StockReportDTO;
import com.investment_qna.model.User;
import com.investment_qna.repository.UserRepository;
import com.investment_qna.service.ReportService;



@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<StockReportDTO> getUserReport(Principal principal) {

    	User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return reportService.getUserReport(user.getId());
    }
    
    
    
    
}
