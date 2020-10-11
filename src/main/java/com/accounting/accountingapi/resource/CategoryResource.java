package com.accounting.accountingapi.resource;

import com.accounting.accountingapi.event.ResourceCreatedEvent;
import com.accounting.accountingapi.mapper.CategoryMapper;
import com.accounting.accountingapi.repository.model.Category;
import com.accounting.accountingapi.resource.dto.CategoryDTO;
import com.accounting.accountingapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Long id) {
        var category = categoryService.findById(id);
        return ResponseEntity.ok().body(categoryMapper.fromModelToDto(category));
    }

    @PostMapping
    public HttpEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO dto, HttpServletResponse response) {
        var categorySaved = categoryMapper.fromModelToDto(categoryService.save(categoryMapper.fromDtoToModel(dto)));

        applicationEventPublisher.publishEvent(new ResourceCreatedEvent(this, response, categorySaved.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(categorySaved);
    }
}