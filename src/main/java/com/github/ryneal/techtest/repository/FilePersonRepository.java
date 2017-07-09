package com.github.ryneal.techtest.repository;

import com.github.ryneal.techtest.exception.PersonDataException;
import com.github.ryneal.techtest.model.Person;
import com.github.ryneal.techtest.repository.io.PersonInputOutputUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
public class FilePersonRepository implements PersonRepository {

    @Autowired
    private PersonInputOutputUtil ioUtil;

    @Override
    public Optional<Person> find(final Long id) throws PersonDataException {
        return findAll().stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public List<Person> findAll() throws PersonDataException {
        List<Person> people = ioUtil.readDataFile();
        if (people == null) {
            return Collections.emptyList();
        }
        return people;
    }

    @Override
    public Person save(final Person person) throws PersonDataException {
        List<Person> people = findAll();

        people = updateListWithPerson(people, person);

        ioUtil.writeToDataFile(people);

        return person;
    }

    protected List<Person> updateListWithPerson(final List<Person> people, final Person person) {
        if (person.getId() == null) {
            Long newId = findAvailableId(people);
            person.setId(newId);
        }

        int index = findIndexOfPersonId(person.getId(), people);

        if (index != -1) {
            people.set(index, person);
            return people;
        }

        List<Person> newPeople = new ArrayList<>(people);
        newPeople.add(person);
        return newPeople;
    }

    @Override
    public void delete(final Long id) throws PersonDataException {
        List<Person> people = findAll();
        int index = findIndexOfPersonId(id, people);
        if (index != -1) {
            people.remove(index);
            ioUtil.writeToDataFile(people);
        }
    }

    private int findIndexOfPersonId(final Long id, final List<Person> people) {
        return IntStream.range(0, people.size())
                .filter(i -> id.equals(people.get(i).getId()))
                .findFirst().orElse(-1);
    }

    private Long findAvailableId(final List<Person> people) {
        boolean found = false;
        Long index = 0L;
        while (!found) {
            index++;
            final Long search = index;
            found = !people.stream().anyMatch(p -> p.getId().equals(search));
        }
        return index;
    }

}
