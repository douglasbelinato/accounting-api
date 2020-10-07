package com.accounting.accountingapi.resource;

import com.accounting.accountingapi.mapper.PersonMapper;
import com.accounting.accountingapi.repository.model.Category;
import com.accounting.accountingapi.repository.model.Person;
import com.accounting.accountingapi.resource.dto.CategoryDTO;
import com.accounting.accountingapi.resource.dto.PersonDTO;
import com.accounting.accountingapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonResource {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonMapper personMapper;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll() {
        return ResponseEntity.ok().body(personMapper.fromModelListToDtoList(personService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable("id") Long id) {
        Person person = personService.findById(id);
        return ResponseEntity.ok().body(personMapper.fromModelToDto(person));
    }

    @PostMapping
    public ResponseEntity<PersonDTO> save(@Valid @RequestBody PersonDTO personDTO) {
        var personSaved = personMapper.fromModelToDto(personService.save(personMapper.fromDtoToModel(personDTO)));

        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(personSaved.getId()).toUri();

        return ResponseEntity.created(uri).body(personSaved);
    }
}
