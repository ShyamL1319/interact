package com.interact.interact.service;

import com.interact.interact.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Long categoryId);
    void deleteCategory(Long categoryId);
}
