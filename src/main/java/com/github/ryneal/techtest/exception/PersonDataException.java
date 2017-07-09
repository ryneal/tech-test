package com.github.ryneal.techtest.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Error reading/writing Person data")
public class PersonDataException extends Exception {
}
