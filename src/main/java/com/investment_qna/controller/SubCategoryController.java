package com.investment_qna.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.investment_qna.model.Category;
import com.investment_qna.model.SubCategory;
import com.investment_qna.repository.CategoryRepository;
import com.investment_qna.repository.SubCategoryRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/subcategories")
@Tag(name = "Subcategories", description = "APIs for managing subcategories under categories")
@CrossOrigin(origins = "http://localhost:3000")
public class SubCategoryController {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    @Operation(summary = "Create subcategory", description = "Add a new subcategory under a category")
    public SubCategory createSubCategory(@RequestBody SubCategory subCategory) {
        Category category = categoryRepository.findById(subCategory.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        subCategory.setCategory(category);
        return subCategoryRepository.save(subCategory);
    }

    @GetMapping("/by-category/{categoryId}")
    @Operation(summary = "Get subcategories by category", description = "Fetch all subcategories for a given category")
    public List<SubCategory> getSubCategoriesByCategory(@PathVariable Long categoryId) {
        return subCategoryRepository.findByCategoryId(categoryId);
    }

    @GetMapping
    @Operation(summary = "Get all subcategories", description = "Fetch all subcategories across all categories")
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }
}
