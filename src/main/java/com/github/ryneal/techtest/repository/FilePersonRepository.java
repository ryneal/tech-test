package com.github.ryneal.techtest.repository;

import com.github.ryneal.techtest.model.Person;
import com.github.ryneal.techtest.repository.io.PersonInputOutputUtil;
import com.google.common.collect.ImmutableList;
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
    public Optional<Person> find(Long id) {
        return findAll().stream().filter(p -> p.getId().equals(id)).findFirst();
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
        int size = people.size();

        if (person.getId() == null) {
            Long newId = findAvailableId(people);
            person.setId(newId);
        }

        int index = findIndexOfPersonId(person.getId(), people);

        if (index != -1 && size > 0) {
            people.set(index, person);
        } else {
            if(size == 0) {
                people = new ArrayList<>();
            }
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

    private int findIndexOfPersonId(final Long id, final List<Person> people) {
        return IntStream.range(0, people.size())
                .filter(i -> id.equals(people.get(i).getId()))
                .findFirst().orElse(-1);
    }

    private Long findAvailableId(final List<Person> people) {
        boolean found = false;
        Long index = 0L;
        while(!found) {
            index++;
            final Long search = index;
            found = !people.stream().anyMatch(p -> p.getId().equals(search));
        }
        return index;
    }

}
