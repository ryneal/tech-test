package com.github.ryneal.techtest.repository;

import com.github.ryneal.techtest.model.Person;

import java.util.List;

public interface PersonRepository {
    Person find(Long id);
    List<Person> findAll();
    Person save(Person person);
    void delete(Long id);
}
