package com.github.ryneal.techtest.controller;

import com.github.ryneal.techtest.exception.PersonNotFoundException;
import com.github.ryneal.techtest.model.Person;
import com.github.ryneal.techtest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("people/list", "people", personRepository.findAll());
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) throws PersonNotFoundException {
        Optional<Person> person = personRepository.find(id);
        return new ModelAndView("people/edit", "person",
                person.orElseThrow(PersonNotFoundException::new));
    }

    @GetMapping("create")
    public ModelAndView create() {
        return new ModelAndView("people/edit", "person", new Person());
    }

    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id) throws PersonNotFoundException {
        Optional<Person> person = personRepository.find(id);
        return new ModelAndView("people/view", "person",
                person.orElseThrow(PersonNotFoundException::new));
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        personRepository.delete(id);
        List<Person> people = personRepository.findAll();
        return new ModelAndView("people/list", "people", people);
    }

    public ModelAndView create(Person person) {
        Person created = personRepository.save(person);
        return new ModelAndView("redirect:/{person.id}", "person.id", created.getId());
    }

    @PostMapping
    public ModelAndView create(@Valid Person person, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("people/edit");
            mav.addObject("person", person);
            return mav;
        }
        return create(person);
    }

}
