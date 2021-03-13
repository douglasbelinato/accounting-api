package com.accounting.accountingapi.application.service.impl;

import com.accounting.accountingapi.infrastructure.exception.ResourceNotFoundException;
import com.accounting.accountingapi.infrastructure.repository.CategoryRepository;
import com.accounting.accountingapi.domain.Category;
import com.accounting.accountingapi.application.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
