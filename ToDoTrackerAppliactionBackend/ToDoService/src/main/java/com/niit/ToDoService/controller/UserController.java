package com.niit.ToDoService.controller;

import com.niit.ToDoService.domain.Task;
import com.niit.ToDoService.domain.User;
import com.niit.ToDoService.exceptions.TaskNotFoundException;
import com.niit.ToDoService.exceptions.UserAlreadyExistException;
import com.niit.ToDoService.exceptions.UserNotFoundException;
import com.niit.ToDoService.service.SequenceGeneratorService;
import com.niit.ToDoService.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;










import java.text.ParseException;


@RestController
@CrossOrigin(origins = "http://localhost:4200/")

@RequestMapping("api/v2/")
public class UserController {
    UserService userService;
    SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    public UserController(UserService userService,SequenceGeneratorService sequenceGeneratorService) {
        this.userService = userService;
        this.sequenceGeneratorService=sequenceGeneratorService;
    }

    public ResponseEntity responseEntity;

    @PostMapping("/register")
    public ResponseEntity<?> userRegistry(@RequestBody User user) throws UserAlreadyExistException {
        try{

            responseEntity= new ResponseEntity(userService.registerNewUser(user), HttpStatus.CREATED);
        }
        catch (UserAlreadyExistException u){

            throw new UserAlreadyExistException();
        }
        return responseEntity;
    }

    @PostMapping("/user/officialTask/{userEmail}")
    public ResponseEntity<?> saveOfficialTask(@RequestBody Task officialTask,@PathVariable String userEmail) throws UserNotFoundException{
        try{
            officialTask.setTaskId(sequenceGeneratorService.getSequenceNumber(officialTask.SEQUENCE_NAME));
            responseEntity= new ResponseEntity(userService.saveOfficialTask(userEmail,officialTask), HttpStatus.CREATED);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @PostMapping("/user/personalTask/{userEmail}")
    public ResponseEntity<?> savePersonalTask(@RequestBody Task personalTask,@PathVariable String userEmail) throws UserNotFoundException {
        try{
            personalTask.setTaskId(sequenceGeneratorService.getSequenceNumber(personalTask.SEQUENCE_NAME));
            responseEntity= new ResponseEntity(userService.savePersonalTask(userEmail,personalTask), HttpStatus.CREATED);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @DeleteMapping("/user/officialTask/{userEmail}/{taskId}")
    public ResponseEntity<?> deleteOfficialTask(@PathVariable String userEmail,@PathVariable int taskId) throws UserNotFoundException, TaskNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.deleteOfficialTask(userEmail,taskId), HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @DeleteMapping("/user/personalTask/{userEmail}/{taskId}")
    public ResponseEntity<?> deletePersonalTask(@PathVariable String userEmail,@PathVariable int taskId) throws UserNotFoundException, TaskNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.deletePersonalTask(userEmail,taskId), HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @PutMapping("/user/officialTask/{userEmail}/{taskId}")
    public ResponseEntity<?> updateOfficialTask(@RequestBody Task officialTask,@PathVariable String userEmail,@PathVariable int taskId) throws UserNotFoundException{
        try{
            responseEntity=new ResponseEntity(userService.updateOfficialTask(userEmail,officialTask, taskId),HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        return responseEntity;
    }
    @PutMapping("/user/personalTask/{userEmail}/{taskId}")
    public ResponseEntity<?> updatePersonalTask(@RequestBody Task personalTask,@PathVariable String userEmail, @PathVariable int taskId) throws UserNotFoundException{
        try{
            responseEntity=new ResponseEntity(userService.updatePersonalTask(userEmail,personalTask,taskId),HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @GetMapping("/user/officialTask/{userEmail}")
    public ResponseEntity<?> getAllOfficialTask(@PathVariable String userEmail) throws UserNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.getAllOfficialTasks(userEmail),HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        return responseEntity;
    }
    @GetMapping("/user/personalTask/{userEmail}")
    public ResponseEntity<?> getAllPersonalTask(@PathVariable String userEmail) throws UserNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.getAllPersonalTasks(userEmail),HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @GetMapping("/user/sortedOfficialTask/{userEmail}")
    public ResponseEntity<?> getAllSortedOfficialTask(@PathVariable String userEmail) throws UserNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.sortByPriorityOfOfficialTask(userEmail),HttpStatus.OK);
        }catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @GetMapping("/user/sortedPersonalTask/{userEmail}")
    public ResponseEntity<?> getAllSortedPersonalTask(@PathVariable String userEmail) throws UserNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.sortByPriorityOfPersonalTask(userEmail),HttpStatus.OK);
        }catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        return responseEntity;
    }
    @DeleteMapping("/user/complete/officialTask/{userEmail}/{taskId}")
    public ResponseEntity<?> completeOfficialTask(@PathVariable String userEmail,@PathVariable int taskId) throws UserNotFoundException, TaskNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.completeOfficialTask(userEmail,taskId), HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        catch (TaskNotFoundException t){
            throw new TaskNotFoundException();
        }
        return responseEntity;
    }

    @DeleteMapping("/user/complete/personalTask/{userEmail}/{taskId}")
    public ResponseEntity<?> completePersonalTask(@PathVariable String userEmail,@PathVariable int taskId) throws UserNotFoundException, TaskNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.completePersonalTask(userEmail,taskId), HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        catch (TaskNotFoundException t){
            throw new TaskNotFoundException();
        }
        return responseEntity;
    }

    @GetMapping("/user/personalTaskById/{userEmail}/{taskId}")
    public ResponseEntity<?> getPersonalTaskById(@PathVariable String userEmail,@PathVariable int taskId) throws UserNotFoundException, TaskNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.getPersonalTaskById(taskId,userEmail), HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        catch (TaskNotFoundException t){
            throw new TaskNotFoundException();
        }
        return responseEntity;
    }
    @GetMapping("/user/all")
    public ResponseEntity<?> getAllUsers() throws UserNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.getAllUsers(),HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @GetMapping("/user/officialTaskById/{userEmail}/{taskId}")
    public ResponseEntity<?> getOfficialTaskById(@PathVariable String userEmail,@PathVariable int taskId) throws UserNotFoundException, TaskNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.getOfficialTaskById(taskId,userEmail), HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        catch (TaskNotFoundException t){
            throw new TaskNotFoundException();
        }
        return responseEntity;
    }

    @GetMapping("/user/notifications/{userEmail}")
    public ResponseEntity<?> getAllNotifiations(@PathVariable String userEmail) throws UserNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.getAllTasksNotificationsList(userEmail),HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        catch (ParseException p){
            p.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping("/user/pending/officialTask/{userEmail}")
    public ResponseEntity<?> getPendingOfficialTasks(@PathVariable String userEmail) throws UserNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.getPendingOfficialTaskList(userEmail),HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        catch (ParseException p){
            p.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping("/user/pending/personalTask/{userEmail}")
    public ResponseEntity<?> getPendingPersonalTasks(@PathVariable String userEmail) throws UserNotFoundException{
        try{
            responseEntity= new ResponseEntity(userService.getPendingPersonalTaskList(userEmail),HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        catch (ParseException p){
            p.printStackTrace();
        }
        return responseEntity;
    }
}
