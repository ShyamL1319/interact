package com.interact.interact.impl;

import com.interact.interact.dto.CategoryDto;
import com.interact.interact.entity.Category;
import com.interact.interact.exception.ResourceNotFoundException;
import com.interact.interact.repository.CategoryRepository;
import com.interact.interact.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto,Category.class);
        category = this.categoryRepository.save(category);
        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","category id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category savedCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List <Category> categories = this.categoryRepository.findAll();
        return categories.stream().map((category)-> {
            return this.modelMapper.map(category,CategoryDto.class);
        }).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Class<CategoryDto> categoryDtoClass = CategoryDto.class;
        return this.modelMapper.map(
                this.categoryRepository
                        .findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category","category id",categoryId)),
                CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","category id",categoryId));
        this.categoryRepository.delete(cat);
    }
}
