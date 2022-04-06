package com.niit.ToDoService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason="***  Task Not Found  ***")
public class TaskNotFoundException extends Exception{
}
