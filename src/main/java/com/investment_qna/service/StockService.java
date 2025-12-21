package com.investment_qna.service;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    @Value("${alphavantage.api.key}")
    private String apiKey;

    private static final String BASE_URL =
            "https://archives.nseindia.com/content/equities/EQUITY_L.csv";
    // 🔥 In-memory cache
    private List<Map<String, String>> cachedStocks;

    // ===============================
    // PUBLIC PAGINATED METHOD
    // ===============================
    public Map<String, Object> getStocks(int page, int size, String search) {
        if (cachedStocks == null) {
            cachedStocks = fetchAllStocksFromApi();
            logger.info("Stocks cached in memory: {}", cachedStocks.size());
        }

        // Filter by search term
        List<Map<String, String>> filtered = cachedStocks;
        if (search != null && !search.isEmpty()) {
            String lowerSearch = search.toLowerCase();
            filtered = cachedStocks.stream()
                    .filter(s -> s.get("name").toLowerCase().contains(lowerSearch)
                            || s.get("symbol").toLowerCase().contains(lowerSearch))
                    .toList();
        }

        int totalRecords = filtered.size();
        int totalPages = (int) Math.ceil((double) totalRecords / size);
        int fromIndex = Math.min(page * size, totalRecords);
        int toIndex = Math.min(fromIndex + size, totalRecords);

        List<Map<String, String>> pageData = filtered.subList(fromIndex, toIndex);

        Map<String, Object> response = new HashMap<>();
        response.put("data", pageData);
        response.put("page", page);
        response.put("size", size);
        response.put("totalRecords", totalRecords);
        response.put("totalPages", totalPages);

        return response;
    }


    // ===============================
    // PRIVATE API FETCH
    // ===============================
    private List<Map<String, String>> fetchAllStocksFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        String csvData = restTemplate.getForObject(BASE_URL, String.class);

        if (csvData == null || csvData.isEmpty()) {
            throw new RuntimeException("Empty response from stock API");
        }

        return parseCsv(csvData);
    }

    // ===============================
    // CSV PARSER
    // ===============================
    private List<Map<String, String>> parseCsv(String csvData) {
        List<Map<String, String>> stocks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new StringReader(csvData))) {

            reader.readLine(); // Skip header
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1);
                if (values.length < 4) continue;

                Map<String, String> stock = new HashMap<>();
                stock.put("symbol", values[0]);
                stock.put("name", values[1]);
                stock.put("exchange", values[2]);
                stock.put("assetType", values[3]);

                stocks.add(stock);
            }

        } catch (Exception e) {
            logger.error("CSV parsing error", e);
            throw new RuntimeException("Failed to parse stock data");
        }

        return stocks;
    }
    
    private static final String NSE_STOCK_LIST_URL =
            "https://www.nseindia.com/api/equity-stockIndices?index=NIFTY%2050"; // Example for Nifty 50

    public List<Map<String, String>> fetchIndianStocks() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            Map<String, Object> response = restTemplate.getForObject(NSE_STOCK_LIST_URL, Map.class);
            if (response == null || !response.containsKey("data")) {
                throw new RuntimeException("Invalid response from NSE");
            }

            List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");

            return data.stream().map(item -> {
                Map<String, String> stock = new HashMap<>();
                stock.put("symbol", (String) item.get("symbol"));
                stock.put("name", (String) item.get("companyName"));
                stock.put("exchange", "NSE");
                stock.put("assetType", "Stock");
                return stock;
            }).collect(Collectors.toList());

        } catch (RestClientException e) {
            logger.error("Error fetching NSE stocks: {}", e.getMessage());
            throw new RuntimeException("Unable to fetch NSE stock data");
        }
    }

}
