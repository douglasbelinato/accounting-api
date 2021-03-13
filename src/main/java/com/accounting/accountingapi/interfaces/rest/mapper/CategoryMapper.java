package com.accounting.accountingapi.interfaces.rest.mapper;

import com.accounting.accountingapi.domain.Category;
import com.accounting.accountingapi.interfaces.rest.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper { //NOSONAR

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO fromModelToDto(Category category);

    Category fromDtoToModel(CategoryDTO dto);
}
