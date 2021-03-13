package com.accounting.accountingapi.interfaces.rest;

import com.accounting.accountingapi.interfaces.rest.event.ResourceCreatedEvent;
import com.accounting.accountingapi.infrastructure.exception.dto.ErrorResponseDTO;
import com.accounting.accountingapi.interfaces.rest.mapper.CategoryMapper;
import com.accounting.accountingapi.domain.Category;
import com.accounting.accountingapi.interfaces.rest.dto.CategoryDTO;
import com.accounting.accountingapi.application.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Category", description = "Operations for category")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Operation(summary = "List categories", description = "List all categories saved in our database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search was successful",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Category.class))))
    })
    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }


    @Operation(summary = "Find by id", description = "Find a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search was successful",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Long id) {
        var category = categoryService.findById(id);
        return ResponseEntity.ok().body(categoryMapper.fromModelToDto(category));
    }


    @Operation(summary = "Save category", description = "Save a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New category successfully saved",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error - Please verify response description for more details",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public HttpEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO dto, HttpServletResponse response) {
        var categorySaved = categoryMapper.fromModelToDto(categoryService.save(categoryMapper.fromDtoToModel(dto)));

        applicationEventPublisher.publishEvent(new ResourceCreatedEvent(this, response, categorySaved.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(categorySaved);
    }
}