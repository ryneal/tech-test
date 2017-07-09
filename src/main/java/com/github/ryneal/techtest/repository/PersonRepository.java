package com.github.ryneal.techtest.repository;

import com.github.ryneal.techtest.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    Optional<Person> find(Long id);
    List<Person> findAll();
    Person save(Person person);
    void delete(Long id);
}
