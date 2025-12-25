package com.investment_qna.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@DeleteMapping("/{stockSymbol}")
	public ResponseEntity<?> deleteReport(@PathVariable String stockSymbol, Principal principal) {
		User user = userRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new RuntimeException("User not found"));
		reportService.deleteReport(user.getId(), stockSymbol);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/metrics")
	public ResponseEntity<Map<String, Object>> getDashboardMetrics(Principal principal) {
	    User user = userRepository.findByEmail(principal.getName())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    List<StockReportDTO> reports = reportService.getUserReport(user.getId());

	    int totalReports = reports.size();
	    int totalStocks = reports.size(); // 1 report per stock
	    Map<String, Long> categoryCount = reports.stream()
	            .flatMap(r -> r.getSubcategories().stream())
	            .collect(Collectors.groupingBy(sub -> sub.getSubCategoryName(), Collectors.counting()));

	    String mostAnalyzedCategory = categoryCount.entrySet().stream()
	            .max(Map.Entry.comparingByValue())
	            .map(Map.Entry::getKey)
	            .orElse("—");

	    Map<String, Object> metrics = new HashMap<>();
	    metrics.put("totalReports", totalReports);
	    metrics.put("totalStocks", totalStocks);
	    metrics.put("mostAnalyzedCategory", mostAnalyzedCategory);

	    return ResponseEntity.ok(metrics);
	}



}
