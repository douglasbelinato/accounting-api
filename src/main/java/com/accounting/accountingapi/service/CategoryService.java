package com.accounting.accountingapi.service;

import com.accounting.accountingapi.repository.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

}
