package com.accounting.accountingapi.service;

import com.accounting.accountingapi.repository.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Category category);

    Category save(Category category);

}
