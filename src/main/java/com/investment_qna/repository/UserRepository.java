package com.investment_qna.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investment_qna.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
