package com.github.ryneal.techtest.controller;

import com.github.ryneal.techtest.model.Person;
import com.github.ryneal.techtest.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class PersonControllerTest {

    private PersonController personController = new PersonController();

    @MockBean
    private PersonService personService;

    @Before
    public void setUp() throws Exception {
        personService = mock(PersonService.class);
    }

    @Test
    public void shouldGetListOfPeople() throws Exception {
        List<Person> actual = Arrays.asList(new Person(), new Person(), new Person());
        when(personService.getPeople()).thenReturn(actual);

        ModelAndView modelAndView = personController.list();
        Map<String, Object> model = modelAndView.getModel();
        String viewName = modelAndView.getViewName();
        Object result = model.get("people");

        assertThat(actual, is(result));
        assertThat("people/list", is(viewName));
        verify(personService.getPeople());
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void shouldGetViewOfPerson() throws Exception {
        Person actual = new Person();

        ModelAndView modelAndView = personController.view(actual);
        Map<String, Object> model = modelAndView.getModel();
        String viewName = modelAndView.getViewName();
        Object result = model.get("person");

        assertThat(actual, is(result));
        assertThat("people/view", is(viewName));
    }

    @Test
    public void shouldDeletePersonAndRedirectToListOfPeople() throws Exception {
        Long id = 66L;

        List<Person> actual = Arrays.asList(new Person());
        when(personService.getPeople()).thenReturn(actual);

        ModelAndView modelAndView = personController.delete(id);
        Map<String, Object> model = modelAndView.getModel();
        String viewName = modelAndView.getViewName();
        Object result = model.get("people");

        assertThat(actual, is(result));
        assertThat("people/list", is(viewName));
        verify(personService.getPeople());
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void shouldCreatePersonAndRedirectToViewPerson() throws Exception {
        Person actual = new Person();
        actual.setId(86L);

        ModelAndView modelAndView = personController.create(actual);
        Map<String, Object> model = modelAndView.getModel();
        Object result = model.get("person.id");
        String viewName = modelAndView.getViewName();

        assertThat(actual, is(result));
        assertThat(viewName, is("redirect:/{person.id}"));
        verify(personService).save(actual);
        verifyNoMoreInteractions(personService);
    }

}