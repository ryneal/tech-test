package com.github.ryneal.techtest.model;

import org.hibernate.validator.constraints.NotEmpty;

public class Person {

    private Long id;

    @NotEmpty(message = "First Name is required.")
    private String firstname;

    @NotEmpty(message = "Surname is required.")
    private String surname;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }
}
