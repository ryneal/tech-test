package com.github.ryneal.techtest.repository;

import com.github.ryneal.techtest.model.Person;
import com.github.ryneal.techtest.repository.io.PersonInputOutputUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FilePersonRepositoryTest {

    @Mock
    PersonInputOutputUtil ioUtil;

    @InjectMocks
    FilePersonRepository personRepository = new FilePersonRepository();

    @Test
    public void shouldFindPersonFromIdWhenAvailable() throws Exception {
        Person actual = new Person();
        actual.setId(1L);
        List<Person> list = Collections.singletonList(actual);
        when(ioUtil.readDataFile()).thenReturn(list);

        Optional<Person> result = personRepository.find(1L);

        assertThat(actual, is(result.get()));
        verify(ioUtil).readDataFile();
        verifyNoMoreInteractions(ioUtil);
    }

    @Test
    public void shouldFindReturnNullWhenPersonNotAvailable() throws Exception {
        Person person = new Person();
        person.setId(1L);
        List<Person> list = Collections.singletonList(person);
        when(ioUtil.readDataFile()).thenReturn(list);

        Optional<Person> result = personRepository.find(2L);

        assertThat(result.get(), is(nullValue()));
        verify(ioUtil).readDataFile();
        verifyNoMoreInteractions(ioUtil);
    }

    @Test
    public void shouldReturnListOfPeople() throws Exception {
        List<Person> actual = createTestArray();
        when(ioUtil.readDataFile()).thenReturn(actual);

        List<Person> result = personRepository.findAll();

        assertThat(actual, is(result));
        verify(ioUtil).readDataFile();
        verifyNoMoreInteractions(ioUtil);
    }

    @Test
    public void shouldReturnEmptyListWhenNoListAvailable() throws Exception {
        when(ioUtil.readDataFile()).thenReturn(null);

        List<Person> result = personRepository.findAll();

        assertThat(0, is(result.size()));
        verify(ioUtil).readDataFile();
        verifyNoMoreInteractions(ioUtil);
    }

    @Test
    public void shouldSaveNewPersonWhenIdNotSet() throws Exception {
        List<Person> list = new ArrayList<>(createTestArray());
        List<Person> people = spy(list);
        when(ioUtil.readDataFile()).thenReturn(people);
        Person actual = new Person();
        actual.setFirstname("Test");
        actual.setSurname("Test");
        Person person = spy(actual);

        personRepository.save(person);

        assertThat(people.size(), is(4));
        verify(ioUtil).readDataFile();
        verify(people).get(2);
        verify(person).setId(4L);
        verify(people).add(person);
        verify(ioUtil).writeToDataFile(people);
        verifyNoMoreInteractions(ioUtil);
    }

    @Test
    public void shouldSaveNewPersonWhenIdNotSetAndArrayIsEmpty() throws Exception {
        List<Person> list = new ArrayList<>(Collections.emptyList());
        List<Person> people = spy(list);
        when(ioUtil.readDataFile()).thenReturn(people);
        Person actual = new Person();
        actual.setFirstname("Test");
        actual.setSurname("Test");

        personRepository.save(actual);

        verify(ioUtil).readDataFile();
        verify(ioUtil).writeToDataFile(any());
        verifyNoMoreInteractions(ioUtil);
    }

    @Test
    public void shouldAddPersonToListWithAllValuesSet() throws Exception {
        List<Person> people = new ArrayList<>(createTestArray());
        Person person = new Person();
        person.setId(9L);
        person.setFirstname("Test");
        person.setSurname("Tester");

        List<Person> result = personRepository.updateListWithPerson(people, person);

        assertThat(result.size(), is(4));
        assertThat(result.get(3), is(person));
    }

    @Test
    public void shouldAddPersonToListWithNoIdSet() throws Exception {
        List<Person> people = new ArrayList<>(createTestArray());
        Person person = new Person();
        person.setFirstname("Test");
        person.setSurname("Tester");

        List<Person> result = personRepository.updateListWithPerson(people, person);

        assertThat(result.size(), is(4));
        assertThat(result.get(3), is(person));
    }

    @Test
    public void shouldReplacePersonWhenAlreadyExists() throws Exception {
        List<Person> people = new ArrayList<>(createTestArray());
        Person person = new Person();
        person.setId(1L);
        person.setFirstname("Test");
        person.setSurname("Tester");

        List<Person> result = personRepository.updateListWithPerson(people, person);

        assertThat(result.size(), is(3));
        assertThat(result.get(0), is(person));
    }

    @Test
    public void shouldUpdatePersonWhenExistingIdSet() throws Exception {
        List<Person> list = new ArrayList<>(createTestArray());
        List<Person> people = spy(list);
        when(ioUtil.readDataFile()).thenReturn(people);
        Person actual = new Person();
        actual.setId(1L);
        actual.setFirstname("Test");
        actual.setSurname("Test");
        Person person = spy(actual);

        personRepository.save(person);

        assertThat(people.size(), is(3));
        verify(ioUtil).readDataFile();
        verify(people).get(0);
        assertThat(people.get(0).getFirstname(), is("Test"));
        assertThat(people.get(0).getSurname(), is("Test"));
        verify(ioUtil).writeToDataFile(people);
        verifyNoMoreInteractions(ioUtil);
    }

    @Test
    public void shouldSaveNewPersonWhenNonExistingIdSet() throws Exception {
        List<Person> list = new ArrayList<>(createTestArray());
        List<Person> people = spy(list);
        when(ioUtil.readDataFile()).thenReturn(people);
        Person actual = new Person();
        actual.setId(4L);
        actual.setFirstname("Test");
        actual.setSurname("Test");
        Person person = spy(actual);

        personRepository.save(person);

        assertThat(people.size(), is(4));
        verify(ioUtil).readDataFile();
        assertThat(people.get(3).getFirstname(), is("Test"));
        assertThat(people.get(3).getSurname(), is("Test"));
        verify(ioUtil).writeToDataFile(people);
        verifyNoMoreInteractions(ioUtil);
    }

    @Test
    public void shouldDeletePersonIfExists() throws Exception {
        List<Person> list = new ArrayList<>(createTestArray());
        List<Person> people = spy(list);
        when(ioUtil.readDataFile()).thenReturn(people);

        personRepository.delete(1L);

        verify(people).remove(0);
        verify(ioUtil).readDataFile();
        verify(ioUtil).writeToDataFile(people);
        verifyNoMoreInteractions(ioUtil);
        assertThat(people.size(), is(2));
    }

    @Test
    public void shouldNotDeleteAnyoneIfDoesNotExist() throws Exception {
        List<Person> list = new ArrayList<>(createTestArray());
        List<Person> people = spy(list);
        when(ioUtil.readDataFile()).thenReturn(people);

        personRepository.delete(4L);

        verify(ioUtil).readDataFile();
        verifyNoMoreInteractions(ioUtil);
        assertThat(people.size(), is(3));
    }

    private Person createTestPerson(Long id, String text) {
        Person person = new Person();
        person.setId(id);
        person.setFirstname(text);
        person.setSurname(text);
        return person;
    }

    private List<Person> createTestArray() {
        return Arrays.asList(
                createTestPerson(1L, "test"),
                createTestPerson(2L, "testing"),
                createTestPerson(3L, "123"));
    }

}