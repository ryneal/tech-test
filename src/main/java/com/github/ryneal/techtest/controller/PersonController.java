package com.github.ryneal.techtest.controller;

import com.github.ryneal.techtest.model.Person;
import com.github.ryneal.techtest.repository.PersonRepository;
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
    private PersonRepository personRepository;

    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("people/list", "people", personRepository.findAll());
    }

    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        Person person = personRepository.find(id);
        return new ModelAndView("people/view", "person", person);
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        personRepository.delete(id);
        List<Person> people = personRepository.findAll();
        return new ModelAndView("people/list", "people", people);
    }

    @PostMapping
    public ModelAndView create(Person person) {
        Person created = personRepository.save(person);
        return new ModelAndView("redirect:/{person.id}", "person.id", created.getId());
    }
}
