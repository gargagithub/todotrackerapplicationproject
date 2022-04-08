package com.example.userauthentication.controller;

import com.example.userauthentication.exception.UserAlreadyExistException;
import com.example.userauthentication.exception.UserNotFoundException;
import com.example.userauthentication.model.User;
import com.example.userauthentication.service.SecurityTokenGenerator;
import com.example.userauthentication.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController //it is a combination of @controller and @ResponseBody annotations
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {

    private UserService userService;
    private SecurityTokenGenerator securityTokenGenerator;

    @Autowired
    public UserController(UserService userService, SecurityTokenGenerator securityTokenGenerator) {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<?>saveUser(@RequestBody User user) throws UserAlreadyExistException
    {
        try {
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        }
        catch (UserAlreadyExistException u){
            throw new UserAlreadyExistException();
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("try after some time",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/login")
    @HystrixCommand(fallbackMethod = "fallbackLogin")
    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value ="3000")
    public ResponseEntity loginUser(@RequestBody User user) throws UserNotFoundException {
        Map<String,String> map=null;
        try{
            User userObj=userService.findByUserEmailAndPassword(user.getUserEmail(),user.getPassword());
            if(userObj.getUserEmail().equals(user.getUserEmail())) {
                map= securityTokenGenerator.generateToken(user);
            }
            return new ResponseEntity(map,HttpStatus.OK);
        }
         catch (UserNotFoundException e) {
             throw new UserNotFoundException();
         }
        catch (Exception e) {
            return new ResponseEntity("try after some time",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> fallbackLogin(@RequestBody User user){
        String message="login failed";
        return new ResponseEntity<>(message,HttpStatus.GATEWAY_TIMEOUT);
    }

    @PutMapping("/userUpdate/{userEmail}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String userEmail) throws UserNotFoundException
    {
        try {
            return new ResponseEntity<>(userService.updateUser(user,userEmail), HttpStatus.OK);
        }
        catch (UserNotFoundException u){
            throw new UserNotFoundException();
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("try after some time",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/api/v1/userservice/users")
    public  ResponseEntity<?>getAllUsers()
    {
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }

    @GetMapping("/userservice/users/{userEmail}")
    public ResponseEntity getUserByEmail(@PathVariable String userEmail) throws UserNotFoundException{
        try{
            return  new ResponseEntity<>(userService.getUser(userEmail),HttpStatus.OK);
        }
        catch (Exception e){
            return  new ResponseEntity("try after some time",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
