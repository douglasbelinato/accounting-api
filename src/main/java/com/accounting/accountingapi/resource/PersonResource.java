package com.accounting.accountingapi.resource;

import com.accounting.accountingapi.event.ResourceCreatedEvent;
import com.accounting.accountingapi.mapper.PersonMapper;
import com.accounting.accountingapi.repository.model.Category;
import com.accounting.accountingapi.repository.model.Person;
import com.accounting.accountingapi.resource.dto.CategoryDTO;
import com.accounting.accountingapi.resource.dto.PersonDTO;
import com.accounting.accountingapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonResource {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll() {
        return ResponseEntity.ok().body(personMapper.fromModelListToDtoList(personService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable("id") Long id) {
        var person = personService.findById(id);
        return ResponseEntity.ok().body(personMapper.fromModelToDto(person));
    }

    @PostMapping
    public ResponseEntity<PersonDTO> save(@Valid @RequestBody PersonDTO personDTO, HttpServletResponse response) {
        var personSaved = personMapper.fromModelToDto(personService.save(personMapper.fromDtoToModel(personDTO)));

        applicationEventPublisher.publishEvent(new ResourceCreatedEvent(this, response, personSaved.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(personSaved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
         personService.delete(id);
    }
}
