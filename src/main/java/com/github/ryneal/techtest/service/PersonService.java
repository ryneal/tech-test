package com.github.ryneal.techtest.service;

import com.github.ryneal.techtest.model.Person;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Service
public class PersonService {
    public List<Person> getPeople() {
        return null;
    }

    public void save(Person person) {
        throw new NotImplementedException();
    }
}
