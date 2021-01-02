package com.accounting.accountingapi.service;

import com.accounting.accountingapi.exception.ResourceNotFoundException;
import com.accounting.accountingapi.repository.PersonRepository;
import com.accounting.accountingapi.repository.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Test
    public void shouldFindAllPeople() {
        Person person1 = Person.builder().name("João").build();
        Person person2 = Person.builder().name("Maria").build();

        when(personRepository.findAll()).thenReturn(Arrays.asList(person1, person2));

        List<Person> people = personService.findAll();

        assertThat(people).contains(person1, person2);
    }

    @Test
    public void shouldNotFindPeople() {
        when(personRepository.findAll()).thenReturn(Collections.emptyList());

        List<Person> people = personService.findAll();

        assertThat(people).isEmpty();
    }

    @Test
    public void shouldFindAPersonById() {
        Long id = 1L;
        Person person = Person.builder().id(id).build();

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        Person personFound = personService.findById(id);

        assertThat(personFound).isEqualTo(person);
    }

    @Test
    public void shouldNotFindAPersonById() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException expectedException = assertThrows(ResourceNotFoundException.class, () -> personService.findById(1L));

        assertThat(expectedException).isNotNull();
    }

    @Test
    public void shouldSaveAPerson() {
        Person personNew = Person.builder().build();
        Person personSaved = Person.builder().id(1L).build();

        when(personRepository.save(personNew)).thenReturn(personSaved);

        Person person = personService.save(personNew);

        assertThat(person).isEqualTo(personSaved);
    }

    @Test
    public void shouldDeleteAPerson() {
        Long id = 1L;
        doNothing().when(personRepository).deleteById(id);

        personService.delete(id);

        verify(personRepository, times(1)).deleteById(id);
    }

    @Test
    public void shouldNotDeleteAPersonWhenIdDoesNotExist() {
        Long id = 1L;
        doThrow(EmptyResultDataAccessException.class).when(personRepository).deleteById(id);

        ResourceNotFoundException expectedException = assertThrows(ResourceNotFoundException.class, () -> personService.delete(id));

        assertThat(expectedException).isNotNull();
    }

    @Test
    public void shouldUpdateAPerson() {
        Person personUpdated = Person.builder().id(1L).name("Douglas").build();

        when(personRepository.save(personUpdated)).thenReturn(personUpdated);

        Person person = personService.update(personUpdated);

        assertThat(person).isEqualTo(personUpdated);
    }

}
