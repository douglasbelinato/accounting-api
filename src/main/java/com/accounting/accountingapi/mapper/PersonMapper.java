package com.accounting.accountingapi.mapper;

import com.accounting.accountingapi.repository.model.Person;
import com.accounting.accountingapi.resource.dto.PersonDTO;
import com.accounting.accountingapi.resource.dto.PersonPartialUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper { // NOSONAR

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO fromPersonModelToPersonDto(Person person);

    PersonPartialUpdateDTO fromPersonModelToPersonPatchDto(Person person);

    List<PersonDTO> fromPersonModelListToPersonDtoList(List<Person> people);

    Person fromPersonDtoToPersonModel(PersonDTO dto);

}
