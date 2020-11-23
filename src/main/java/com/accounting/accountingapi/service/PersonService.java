package com.accounting.accountingapi.service;

import com.accounting.accountingapi.repository.model.Person;

import java.util.List;

public interface PersonService {

    List<Person> findAll();

    Person findById(Long id);

    Person save(Person person);

    void delete(Long id);

    void update(Long id, Person person);
}
