package com.github.ryneal.techtest.repository;

import com.github.ryneal.techtest.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    Optional<Person> find(final Long id);
    List<Person> findAll();
    Person save(final Person person);
    void delete(final Long id);
}
