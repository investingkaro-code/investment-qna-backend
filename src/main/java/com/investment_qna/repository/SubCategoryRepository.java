package com.investment_qna.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.investment_qna.model.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findByCategoryId(Long categoryId);
}
