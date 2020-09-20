package com.accounting.accountingapi.service;

import com.accounting.accountingapi.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();
}
