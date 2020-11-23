package com.accounting.accountingapi.service.impl;

import com.accounting.accountingapi.exception.ResourceNotFoundException;
import com.accounting.accountingapi.repository.PersonRepository;
import com.accounting.accountingapi.repository.model.Person;
import com.accounting.accountingapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person findById(Long id) {
        return personRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void delete(Long id) {
        try {
            personRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public void update(Long id, Person person) {
        if (person.getId() != id) {

        }

        personRepository.save(person);
//        var personToBeUpdated = findById(id);
//        System.out.println(personToBeUpdated);
//
//        if (jsonMap.isEmpty()) {
//            throw new ResourceNotValidException();
//        }
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        Person personChanged = objectMapper.convertValue(jsonMap, Person.class);
//
//        jsonMap.forEach((propertyName, propertyValue) -> {
//            var field = ReflectionUtils.findField(Person.class, propertyName);
//            field.setAccessible(true);
//
//            var newValue = ReflectionUtils.getField(field, personChanged);
//
//            ReflectionUtils.setField(field, personToBeUpdated, newValue);
//        });
//
//        System.out.println("personChanged=" + personChanged);
//        System.out.println("personToBeUpdated=" + personToBeUpdated);
//        personRepository.save(person);
    }
}
