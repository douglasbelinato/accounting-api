package com.accounting.accountingapi.mapper;

import com.accounting.accountingapi.resource.dto.request.CategoryRequestDTO;
import com.accounting.accountingapi.service.vo.CategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryVOMapper {

    CategoryVOMapper INSTANCE = Mappers.getMapper(CategoryVOMapper.class);

    CategoryVO map(CategoryRequestDTO dto);
}
