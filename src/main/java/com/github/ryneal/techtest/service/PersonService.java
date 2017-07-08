package com.github.ryneal.techtest.service;

import com.github.ryneal.techtest.model.Person;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collections;
import java.util.List;

@Service
public class PersonService {
    public List<Person> getPeople() {
        return Collections.emptyList();
    }

    public Person save(Person person) {
        throw new NotImplementedException();
    }

    public void delete(Long id) {
        throw new NotImplementedException();
    }
}
