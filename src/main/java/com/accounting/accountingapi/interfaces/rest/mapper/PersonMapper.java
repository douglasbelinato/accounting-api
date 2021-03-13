package com.accounting.accountingapi.interfaces.rest.mapper;

import com.accounting.accountingapi.domain.Person;
import com.accounting.accountingapi.interfaces.rest.dto.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper { // NOSONAR

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO fromPersonModelToPersonDto(Person person);

    List<PersonDTO> fromPersonModelListToPersonDtoList(List<Person> people);

    Person fromPersonDtoToPersonModel(PersonDTO dto);

}
