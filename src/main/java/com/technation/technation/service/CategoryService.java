package com.technation.technation.service;

import com.technation.technation.model.Category;
import com.technation.technation.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryById(int categoryId){
        return categoryRepository.findById(categoryId);
    }
}
