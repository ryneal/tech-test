package com.github.ryneal.techtest.controller;

import com.github.ryneal.techtest.exception.PersonNotFoundException;
import com.github.ryneal.techtest.model.Person;
import com.github.ryneal.techtest.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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
        Person person = new Person();
        person.setId(77L);
        Optional<Person> actual = Optional.of(person);
        when(personRepository.find(person.getId())).thenReturn(actual);

        ModelAndView modelAndView = personController.view(person.getId());
        Map<String, Object> model = modelAndView.getModel();
        String viewName = modelAndView.getViewName();
        Object result = model.get("person");

        assertThat(person, is(result));
        assertThat("people/view", is(viewName));
    }

    @Test(expected = PersonNotFoundException.class)
    public void shouldGetThrowExceptionIfNoPersonFound() throws Exception {
        Optional<Person> actual = Optional.empty();
        when(personRepository.find(89L)).thenReturn(actual);

        ModelAndView modelAndView = personController.view(89L);
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

    @Test
    public void shouldGetCreateWithNewPersonObject() throws Exception {
        ModelAndView modelAndView = personController.create();
        Map<String, Object> model = modelAndView.getModel();
        Object result = model.get("person");
        String viewName = modelAndView.getViewName();

        assertThat(result, is(notNullValue()));
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    public void shouldGetEditPageWithExistingPersonObject() throws Exception {
        Person actual = new Person();
        actual.setId(77L);
        when(personRepository.find(actual.getId())).thenReturn(Optional.of(actual));

        ModelAndView modelAndView = personController.edit(77L);
        Map<String, Object> model = modelAndView.getModel();
        String viewName = modelAndView.getViewName();
        Object result = model.get("person");

        assertThat(actual, is(result));
        assertThat("people/edit", is(viewName));
        verify(personRepository).find(77L);
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    public void shouldReturnNoErrorsIfBindingResultErrorsNotSet() throws Exception {
        BindingResult results = mock(BindingResult.class);
        when(results.hasErrors()).thenReturn(false);
        Person actual = new Person();
        actual.setId(888L);
        when(personRepository.save(actual)).thenReturn(actual);

        ModelAndView modelAndView = personController.create(actual, results);
        Map<String, Object> model = modelAndView.getModel();
        Object personId = model.get("person.id");
        String viewName = modelAndView.getViewName();

        assertThat(actual.getId(), is(personId));
        assertThat(viewName, is("redirect:/{person.id}"));
    }

    @Test
    public void shouldReturnErrorsIfBindingResultErrorsNotSet() throws Exception {
        ObjectError error = mock(ObjectError.class);
        BindingResult results = mock(BindingResult.class);
        when(results.hasErrors()).thenReturn(true);
        List<ObjectError> errorList = Collections.singletonList(error);
        when(results.getAllErrors()).thenReturn(errorList);
        Person actual = new Person();
        actual.setId(888L);
        when(personRepository.save(actual)).thenReturn(actual);

        ModelAndView modelAndView = personController.create(actual, results);
        Map<String, Object> model = modelAndView.getModel();
        Object personId = model.get("person.id");
        String viewName = modelAndView.getViewName();

        assertThat(actual.getId(), is(personId));
        assertThat(viewName, is("redirect:edit/{person.id}"));
    }

    @Test
    public void shouldRedirectToCreatePageIfPersonIdIsNotSetAndValidationErrorsAvailable() throws Exception {
        ObjectError error = mock(ObjectError.class);
        BindingResult results = mock(BindingResult.class);
        when(results.hasErrors()).thenReturn(true);
        List<ObjectError> errorList = Collections.singletonList(error);
        when(results.getAllErrors()).thenReturn(errorList);
        Person actual = new Person();
        when(personRepository.save(actual)).thenReturn(actual);

        ModelAndView modelAndView = personController.create(actual, results);
        Map<String, Object> model = modelAndView.getModel();
        Object person = model.get("person");
        String viewName = modelAndView.getViewName();

        assertThat(actual, is(person));
        assertThat(viewName, is("redirect:/create"));
    }

}