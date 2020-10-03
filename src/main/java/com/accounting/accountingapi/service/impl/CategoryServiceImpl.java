package com.accounting.accountingapi.service.impl;

import com.accounting.accountingapi.exception.NotFoundException;
import com.accounting.accountingapi.repository.CategoryRepository;
import com.accounting.accountingapi.repository.model.Category;
import com.accounting.accountingapi.service.CategoryService;
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
    public Category findById(Category category) {
        return categoryRepository.findById(category.getId()).orElseThrow(NotFoundException::new);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
