package com.niit.ToDoService.service;

import com.niit.ToDoService.domain.Task;
import com.niit.ToDoService.domain.User;
import com.niit.ToDoService.exceptions.TaskAlreadyExistException;
import com.niit.ToDoService.exceptions.TaskNotFoundException;
import com.niit.ToDoService.exceptions.UserAlreadyExistException;
import com.niit.ToDoService.exceptions.UserNotFoundException;

import java.text.ParseException;
import java.util.List;

public interface UserService {
    User registerNewUser(User user) throws UserAlreadyExistException;
    User saveOfficialTask(String userEmail, Task task) throws UserNotFoundException;
    User savePersonalTask(String userEmail, Task task) throws UserNotFoundException;
    boolean deleteOfficialTask(String userEmail, int taskId) throws UserNotFoundException, TaskNotFoundException;
    boolean deletePersonalTask(String userEmail, int taskId) throws UserNotFoundException, TaskNotFoundException;
    User updateOfficialTask(String userEmail, Task task, int taskId) throws UserNotFoundException;
    User updatePersonalTask(String userEmail, Task task, int taskId) throws UserNotFoundException;
    List<Task> getAllOfficialTasks(String userEmail) throws UserNotFoundException;
    List<Task> getAllPersonalTasks(String userEmail) throws UserNotFoundException;
    List<Task> sortByPriorityOfOfficialTask(String userEmail) throws UserNotFoundException;
    List<Task> sortByPriorityOfPersonalTask(String userEmail) throws UserNotFoundException;
    Task getOfficialTaskById(int taskId,String userEmail) throws TaskNotFoundException,UserNotFoundException;
    Task getPersonalTaskById(int taskId,String userEmail) throws TaskNotFoundException,UserNotFoundException;
    boolean completeOfficialTask(String userEmail, int taskId) throws UserNotFoundException,TaskNotFoundException;
    boolean completePersonalTask(String userEmail, int taskId) throws UserNotFoundException,TaskNotFoundException;
    List<User> getAllUsers() throws UserNotFoundException;
    public List<Task> getAllTasksNotificationsList(String userEmail) throws UserNotFoundException, ParseException;
    public List<Task> getPendingOfficialTaskList(String userEmail) throws UserNotFoundException, ParseException;
    public List<Task> getPendingPersonalTaskList(String userEmail) throws UserNotFoundException, ParseException;
}
