package com.niit.ToDoService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.ALREADY_REPORTED, reason = "*** Task Already Exist ***")
public class TaskAlreadyExistException extends Exception{
}
