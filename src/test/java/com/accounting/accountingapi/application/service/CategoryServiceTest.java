package com.accounting.accountingapi.application.service;

import com.accounting.accountingapi.infrastructure.exception.ResourceNotFoundException;
import com.accounting.accountingapi.infrastructure.repository.CategoryRepository;
import com.accounting.accountingapi.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    public void shouldFindAllCategories() {
        Category category1 = Category.builder().id(1L).name("Lazer").build();
        Category category2 = Category.builder().id(2L).name("Escritório").build();

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<Category> categories = categoryService.findAll();

        assertThat(categories).contains(category1, category2);
    }

    @Test
    public void shouldNotFindCategories() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        List<Category> categories = categoryService.findAll();

        assertThat(categories).isEmpty();
    }

    @Test
    public void shouldFindACategoryById() {
        Long id = 1L;
        Category category = Category.builder().id(id).build();

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        Category categoryFound = categoryService.findById(id);

        assertThat(categoryFound).isEqualTo(category);
    }

    @Test
    public void shouldNotFindACategoryById() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException expectedException = assertThrows(ResourceNotFoundException.class, () -> categoryService.findById(1L));

        assertThat(expectedException).isNotNull();
    }

    @Test
    public void shouldSaveACategory() {
        Category categoryNew = Category.builder().build();
        Category categorySaved = Category.builder().id(1L).build();

        when(categoryRepository.save(categoryNew)).thenReturn(categorySaved);

        Category category = categoryService.save(categoryNew);

        assertThat(category).isEqualTo(categorySaved);
    }

}
