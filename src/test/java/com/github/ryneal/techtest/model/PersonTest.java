package com.github.ryneal.techtest.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void shouldBeAbleToSetAndGetFirstname() throws Exception {
        Person person = new Person();
        person.setFirstname("Test");

        String result = person.getFirstname();

        assertThat("Test", is(result));
    }

    @Test
    public void shouldBeAbleToSetAndGetSurname() throws Exception {
        Person person = new Person();
        person.setSurname("Test");

        String result = person.getSurname();

        assertThat("Test", is(result));
    }

}