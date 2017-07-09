package com.github.ryneal.techtest.controller;

import com.github.ryneal.techtest.exception.PersonDataException;
import com.github.ryneal.techtest.exception.PersonNotFoundException;
import com.github.ryneal.techtest.model.Person;
import com.github.ryneal.techtest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public ModelAndView list() throws PersonDataException {
        return new ModelAndView("people/list", "people", personRepository.findAll());
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(@PathVariable("id") final Long id) throws PersonNotFoundException, PersonDataException {
        Optional<Person> person = personRepository.find(id);
        return new ModelAndView("people/edit", "person",
                person.orElseThrow(PersonNotFoundException::new));
    }

    @GetMapping("create")
    public ModelAndView create() {
        return new ModelAndView("people/edit", "person", new Person());
    }

    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") final Long id) throws PersonNotFoundException, PersonDataException {
        Optional<Person> person = personRepository.find(id);
        return new ModelAndView("people/view", "person",
                person.orElseThrow(PersonNotFoundException::new));
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(@PathVariable("id") final Long id) throws PersonDataException {
        personRepository.delete(id);
        List<Person> people = personRepository.findAll();
        return new ModelAndView("people/list", "people", people);
    }

    protected ModelAndView create(final Person person) throws PersonDataException {
        final Person created = personRepository.save(person);
        return new ModelAndView("redirect:/{person.id}", "person.id", created.getId());
    }

    @PostMapping
    public ModelAndView create(@Valid final Person person, final BindingResult result) throws PersonDataException {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("people/edit");
            mav.addObject("person", person);
            return mav;
        }
        return create(person);
    }

}
