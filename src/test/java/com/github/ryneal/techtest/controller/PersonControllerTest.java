package com.github.ryneal.techtest.controller;

import com.github.ryneal.techtest.model.Person;
import com.github.ryneal.techtest.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PersonControllerTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonController personController = new PersonController();

    @Test
    public void shouldGetListOfPeople() throws Exception {
        List<Person> actual = Arrays.asList(new Person(), new Person(), new Person());
        when(personRepository.findAll()).thenReturn(actual);

        ModelAndView modelAndView = personController.list();
        Map<String, Object> model = modelAndView.getModel();
        String viewName = modelAndView.getViewName();
        Object result = model.get("people");

        assertThat(actual, is(result));
        assertThat("people/list", is(viewName));
        verify(personRepository).findAll();
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    public void shouldGetViewOfPerson() throws Exception {
        Person actual = new Person();
        actual.setId(77L);
        when(personRepository.find(actual.getId())).thenReturn(actual);

        ModelAndView modelAndView = personController.view(actual.getId());
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
        when(personRepository.findAll()).thenReturn(actual);

        ModelAndView modelAndView = personController.delete(id);
        Map<String, Object> model = modelAndView.getModel();
        String viewName = modelAndView.getViewName();
        Object result = model.get("people");

        assertThat(actual, is(result));
        assertThat("people/list", is(viewName));
        verify(personRepository).delete(66L);
        verify(personRepository).findAll();
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    public void shouldCreatePersonAndRedirectToViewPerson() throws Exception {
        Person actual = new Person();
        actual.setId(86L);
        when(personRepository.save(any())).thenReturn(actual);

        ModelAndView modelAndView = personController.create(actual);
        Map<String, Object> model = modelAndView.getModel();
        Object result = model.get("person.id");
        String viewName = modelAndView.getViewName();

        assertThat(actual.getId(), is(result));
        assertThat(viewName, is("redirect:/{person.id}"));
        verify(personRepository).save(actual);
        verifyNoMoreInteractions(personRepository);
    }

}