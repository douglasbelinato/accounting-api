package com.accounting.accountingapi.application.service;

import com.accounting.accountingapi.domain.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

}
