package com.github.ryneal.techtest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="No such Person")
public class PersonNotFoundException extends Exception {

}
