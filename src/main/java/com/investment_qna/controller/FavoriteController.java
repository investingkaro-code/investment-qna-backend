package com.investment_qna.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment_qna.DTO.FavoriteRequest;
import com.investment_qna.model.Favorite;
import com.investment_qna.service.FavoriteService;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<Void> addFavorite(@RequestBody FavoriteRequest request) {
        favoriteService.addFavorite(
                request.getStockSymbol(),
                request.getStockName()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Favorite>> getFavorites() {
        return ResponseEntity.ok(favoriteService.getFavorites());
    }

    @DeleteMapping("/{symbol}")
    public ResponseEntity<Void> removeFavorite(@PathVariable String symbol) {
        favoriteService.removeFavorite(symbol);
        return ResponseEntity.ok().build();
    }
}

