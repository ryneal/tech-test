package com.github.ryneal.techtest.repository;

import com.github.ryneal.techtest.exception.PersonDataException;
import com.github.ryneal.techtest.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    Optional<Person> find(final Long id) throws PersonDataException;
    List<Person> findAll() throws PersonDataException;
    Person save(final Person person) throws PersonDataException;
    void delete(final Long id) throws PersonDataException;
}
