
package com.investment_qna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investment_qna.model.Favorite;
import com.investment_qna.model.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserAndStockSymbol(User user, String stockSymbol);

    List<Favorite> findAllByUser(User user);

    List<Favorite> findAllByUserId(Long userId);

    long deleteByUserAndStockSymbol(User user, String stockSymbol);
}
