package com.accounting.accountingapi.mapper;

import com.accounting.accountingapi.repository.model.Category;
import com.accounting.accountingapi.resource.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper { //NOSONAR

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO fromModelToDto(Category category);

    Category fromDtoToModel(CategoryDTO dto);
}
