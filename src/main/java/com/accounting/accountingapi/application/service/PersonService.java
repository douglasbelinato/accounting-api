package com.accounting.accountingapi.application.service;

import com.accounting.accountingapi.domain.Person;

import java.util.List;

public interface PersonService {

    List<Person> findAll();

    Person findById(Long id);

    Person save(Person person);

    void delete(Long id);

    Person update(Person person);
}
