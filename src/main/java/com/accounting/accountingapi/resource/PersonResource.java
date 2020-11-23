package com.accounting.accountingapi.resource;

import com.accounting.accountingapi.event.ResourceCreatedEvent;
import com.accounting.accountingapi.exception.EmptyBodyException;
import com.accounting.accountingapi.mapper.PersonMapper;
import com.accounting.accountingapi.repository.model.Person;
import com.accounting.accountingapi.resource.converter.JsonToResourceConverter;
import com.accounting.accountingapi.resource.dto.PersonDTO;
import com.accounting.accountingapi.resource.dto.PersonPartialUpdateDTO;
import com.accounting.accountingapi.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/people")
public class PersonResource {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private Validator validator;

    @Autowired
    private JsonToResourceConverter<PersonPartialUpdateDTO> personJsonToResourceConverter;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll() {
        return ResponseEntity.ok().body(personMapper.fromPersonModelListToPersonDtoList(personService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable("id") Long id) {
        var person = personService.findById(id);
        return ResponseEntity.ok().body(personMapper.fromPersonModelToPersonDto(person));
    }

    @PostMapping
    public ResponseEntity<PersonDTO> save(@Valid @RequestBody PersonDTO personDTO, HttpServletResponse response) {
        var personSaved = personMapper.fromPersonModelToPersonDto(personService.save(personMapper.fromPersonDtoToPersonModel(personDTO)));

        applicationEventPublisher.publishEvent(new ResourceCreatedEvent(this, response, personSaved.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(personSaved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
         personService.delete(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Person> patch(@PathVariable Long id, @RequestBody Map<String, Object> jsonMapResourceUpdated) throws Throwable {
//    public ResponseEntity<Person> patch(@PathVariable Long id, @RequestBody PersonPartiallyUpdatedDTO personPartiallyUpdatedDTO) throws Throwable {
//        var personToBeUpdatedDTO = personMapper.fromPersonModelToPersonPatchDto(personService.findById(id));
//
//        personJsonToResourceConverter.fulfillResourceWithValues(jsonMapResourceUpdated, personToBeUpdatedDTO, PersonPartiallyUpdatedDTO.class);
////        personService.update(id, personMapper.fromDtoToModel(personToBeUpdatedDTO));

//        System.out.println(new ObjectMapper().writeValueAsString(personPartiallyUpdatedDTO));

        if (jsonMapResourceUpdated.isEmpty()) {
            throw new EmptyBodyException();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.convertValue(jsonMapResourceUpdated, PersonPartialUpdateDTO.class);

        String json = new ObjectMapper().writeValueAsString(jsonMapResourceUpdated);

        var personDTO = personMapper.fromPersonModelToPersonDto(personService.findById(id));

        PersonDTO personDTOUpdated = new ObjectMapper().readerForUpdating(personDTO).readValue(json);

        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTOUpdated);
        System.out.println(violations.toString());

        return ResponseEntity.ok().build();
    }
}
