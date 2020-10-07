package com.accounting.accountingapi.service;

import com.accounting.accountingapi.repository.CategoryRepository;
import com.accounting.accountingapi.repository.model.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    public void shoulFindAllCategories() {
        Category category1 = Category.builder().id(1L).name("Lazer").build();
        Category category2 = Category.builder().id(2L).name("Escritório").build();

        Mockito.when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<Category> categories = categoryService.findAll();

        Assertions.assertThat(categories).contains(category1, category2);
    }

    @Test
    public void shouldNotFindCategories() {
        Mockito.when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        List<Category> categories = categoryService.findAll();

        Assertions.assertThat(categories).isEmpty();
    }

}
