package com.accounting.accountingapi.service.impl;

import com.accounting.accountingapi.exception.NotFoundException;
import com.accounting.accountingapi.repository.PersonRepository;
import com.accounting.accountingapi.repository.model.Person;
import com.accounting.accountingapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return personRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }
}
