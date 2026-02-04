package com.investment_qna.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.investment_qna.model.Favorite;
import com.investment_qna.model.User;
import com.investment_qna.repository.FavoriteRepository;
import com.investment_qna.repository.UserRepository;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    public FavoriteService(FavoriteRepository favoriteRepository,
                           UserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = auth.getName(); // usually email or username
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void addFavorite(String stockSymbol, String stockName) {
        User user = getCurrentUser();

        boolean exists = favoriteRepository
                .existsByUserAndStockSymbol(user, stockSymbol);

        if (exists) {
            return; // idempotent (important for UI toggles)
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setStockSymbol(stockSymbol);
        favorite.setStockName(stockName);

        favoriteRepository.save(favorite);
    }

    public List<Favorite> getFavorites() {
        User user = getCurrentUser();
        System.out.println("Fetching favorites for user ID: " + user.getId());
        
        List<Favorite> favorites = favoriteRepository.findAllByUserId(user.getId());
        System.out.println("Found " + favorites.size() + " favorites.");
        return favorites;
    }

    @Transactional
    public void removeFavorite(String stockSymbol) {
    User user = getCurrentUser();

    long deleted = favoriteRepository
            .deleteByUserAndStockSymbol(user, stockSymbol);

    System.out.println(
        "Delete favorite → user=" + user.getId() +
        ", symbol=" + stockSymbol +
        ", deletedRows=" + deleted
    );
}

}

