package com.github.ryneal.techtest.controller;

import com.github.ryneal.techtest.TechTestApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TechTestApplication.class)
public class PersonControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void shouldBeAbleToAccessPeoplePage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>People View All</h1>")));
    }

    @Test
    public void shouldBeAbleToCreateAndRedirectToViewNewPerson() throws Exception {
        this.mockMvc.perform(post("/").param("firstname", "John").param("surname", "Smith"))
                .andExpect((status().is(302)));
    }

    @Test
    public void shouldBeAbleToGoToCreatePage() throws Exception {
        this.mockMvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>Person Edit/Create</h1>")));
    }

    @Test
    public void shouldBeGivenValidationMessageWhenPersonHasNoFirstname() throws Exception {
        this.mockMvc.perform(post("/").param("firstname", "").param("surname", "Smith"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("First Name is required.")));
    }

    @Test
    public void shouldBeGivenValidationMessageWhenPersonHasNoSurname() throws Exception {
        this.mockMvc.perform(post("/").param("firstname", "John").param("surname", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Surname is required.")));
    }

}