package com.github.ryneal.techtest.repository;

import com.github.ryneal.techtest.model.Person;
import com.github.ryneal.techtest.repository.io.PersonInputOutputUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class FilePersonRepository implements PersonRepository {

    @Autowired
    private PersonInputOutputUtil ioUtil;

    @Override
    public Person find(Long id) {
        return findAll().stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Person> findAll() {
        List<Person> people = ioUtil.readDataFile();
        if (people == null) {
            return Collections.emptyList();
        }
        return people;
    }

    @Override
    public Person save(Person person) {
        List<Person> people = findAll();

        if (person.getId() == null) {
            Long lastId = people.get(people.size()-1).getId();
            person.setId(lastId + 1);
        }

        int index = findIndexOfPersonId(person.getId(), people);

        if (index != -1) {
            people.set(index, person);
        } else {
            people.add(person);
        }

        ioUtil.writeToDataFile(people);

        return person;
    }

    @Override
    public void delete(Long id) {
        List<Person> people = findAll();
        int index = findIndexOfPersonId(id, people);
        if (index != -1) {
            people.remove(index);
            ioUtil.writeToDataFile(people);
        }
    }

    private int findIndexOfPersonId(Long id, List<Person> people) {
        return IntStream.range(0, people.size())
                .filter(i -> id.equals(people.get(i).getId()))
                .findFirst().orElse(-1);
    }

}
