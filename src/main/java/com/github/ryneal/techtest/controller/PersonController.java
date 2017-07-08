package com.github.ryneal.techtest.controller;

import com.github.ryneal.techtest.model.Person;
import com.github.ryneal.techtest.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("people/list", "people", personService.getPeople());
    }

    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Person person) {
        return new ModelAndView("people/view", "person", person);
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        personService.delete(id);
        List<Person> people = personService.getPeople();
        return new ModelAndView("people/list", "people", people);
    }

    @PostMapping
    public ModelAndView create(Person person) {
        Person created = personService.save(person);
        return new ModelAndView("redirect:/{person.id}", "person.id", created.getId());
    }
}
