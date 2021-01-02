package com.accounting.accountingapi.resource;

import com.accounting.accountingapi.event.ResourceCreatedEvent;
import com.accounting.accountingapi.mapper.PersonMapper;
import com.accounting.accountingapi.resource.converter.ResourcePartialUpdateConverter;
import com.accounting.accountingapi.resource.dto.PersonDTO;
import com.accounting.accountingapi.resource.dto.PersonPartialUpdateDTO;
import com.accounting.accountingapi.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
    private ResourcePartialUpdateConverter<PersonDTO, PersonPartialUpdateDTO> personPartialUpdateConverter;

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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Long id, @RequestBody Map<String, Object> jsonMapResourceUpdated) throws JsonProcessingException {
        var personDTO = personMapper.fromPersonModelToPersonDto(personService.findById(id));
        PersonDTO personUpdatedDTO = personPartialUpdateConverter.applyFieldsUpdatedToResource(jsonMapResourceUpdated, personDTO, PersonDTO.class, PersonPartialUpdateDTO.class);

        personService.update(personMapper.fromPersonDtoToPersonModel(personUpdatedDTO));
    }

}
