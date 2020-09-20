package com.accounting.accountingapi.service;

import com.accounting.accountingapi.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

}
