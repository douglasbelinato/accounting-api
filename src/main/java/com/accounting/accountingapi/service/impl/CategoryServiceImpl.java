package com.accounting.accountingapi.service.impl;

import com.accounting.accountingapi.model.Category;
import com.accounting.accountingapi.repository.CategoryRepository;
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
}
