package com.niit.ToDoService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT,reason="*** User Already exists ***")
public class UserAlreadyExistException extends Exception {
}
