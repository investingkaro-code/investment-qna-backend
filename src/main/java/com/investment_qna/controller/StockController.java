package com.investment_qna.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.investment_qna.service.StockService;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

//    @GetMapping("/nse")
//    public ResponseEntity<?> getNSEStocks(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            @RequestParam(defaultValue = "") String search
//    ) {
//        try {
//            List<Map<String, String>> stocks = stockService.fetchIndianStocks();
//
//            // Filter by search
//            if (!search.isEmpty()) {
//                stocks = stocks.stream()
//                        .filter(s -> s.get("name").toLowerCase().contains(search.toLowerCase()) ||
//                                     s.get("symbol").toLowerCase().contains(search.toLowerCase()))
//                        .collect(Collectors.toList());
//            }
//
//            int total = stocks.size();
//            int fromIndex = Math.min(page * size, total);
//            int toIndex = Math.min(fromIndex + size, total);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("data", stocks.subList(fromIndex, toIndex));
//            response.put("totalPages", (int) Math.ceil((double) total / size));
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            logger.error("Error in getNSEStocks: {}", e.getMessage());
//            return ResponseEntity.status(500).body(Map.of("error", "Unable to fetch NSE stocks"));
//        }
//    }

    
    @GetMapping
    public ResponseEntity<?> getStocks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") String search
    ) {
        return ResponseEntity.ok(stockService.getStocks(page, size, search));
    }

}
