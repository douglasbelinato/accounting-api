package com.accounting.accountingapi.mapper;

import com.accounting.accountingapi.repository.model.Person;
import com.accounting.accountingapi.resource.dto.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper { // NOSONAR

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO fromModelToDto(Person person);

    List<PersonDTO> fromModelListToDtoList(List<Person> people);

    Person fromDtoToModel(PersonDTO dto);

}
