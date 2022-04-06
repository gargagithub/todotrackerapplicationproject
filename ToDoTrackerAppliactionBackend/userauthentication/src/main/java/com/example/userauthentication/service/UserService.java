package com.example.userauthentication.service;

import com.example.userauthentication.exception.UserAlreadyExistException;
import com.example.userauthentication.exception.UserNotFoundException;
import com.example.userauthentication.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user) throws UserAlreadyExistException;
    User findByUserEmailAndPassword(String userEmail, String passWord)throws UserNotFoundException;
    User updateUser(User user,String userEmail) throws UserNotFoundException ;
    List<User>getAllUser();


}
