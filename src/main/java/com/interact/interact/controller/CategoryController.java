package com.interact.interact.controller;

import com.interact.interact.dto.ApiResponse;
import com.interact.interact.dto.CategoryDto;
import com.interact.interact.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.util.logging.resources.logging_es;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/")
    private ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{categoryId}")
    private ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Long categoryId){
        log.info(String.valueOf(categoryDto));
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto,categoryId);
        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping("/{categoryId}")
    private ResponseEntity<CategoryDto> getCategory(@Valid @PathVariable("categoryId") Long categoryId){
        CategoryDto updatedCategory = this.categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping("/")
    private ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categoryDtos = this.categoryService.getAllCategories();
        return  new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{catId}")
    private ResponseEntity<ApiResponse> deleteCategory(@PathVariable("catId") Long catId){
        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<>(new ApiResponse("Category Deleted Successfully",200,true),HttpStatus.OK);
    }
}
