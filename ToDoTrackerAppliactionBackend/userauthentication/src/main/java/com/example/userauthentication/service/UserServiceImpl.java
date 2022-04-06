package com.example.userauthentication.service;

import com.example.userauthentication.exception.UserAlreadyExistException;
import com.example.userauthentication.exception.UserNotFoundException;
import com.example.userauthentication.model.User;
import com.example.userauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //DURING THE COMPONENT SCANNING SPRING, LOOKS AT THIS ANNOTATION AND TREATS THIS AS ASPRING CONATINERR MAANGED OBJECT
public class UserServiceImpl implements UserService{

   private UserRepository userRepository;

   @Autowired
   public UserServiceImpl(UserRepository userRepository){
       this.userRepository=userRepository;  //at runtime spring provides a userRepository object to the service layer through the construction injection
   }

    @Override
    public User saveUser(User user) throws UserAlreadyExistException {
        if(userRepository.findById(user.getUserEmail()).isPresent()){
            throw new UserAlreadyExistException();
        }
        return userRepository.save(user); // calling save method of jpa repository
    }

    @Override
    public User findByUserEmailAndPassword(String userEmail, String passWord)throws UserNotFoundException
    {
       User user=userRepository.findByUserEmailAndPassword(userEmail,passWord);
       if (user==null){
           throw new UserNotFoundException();
       }
        return user;
    }

    @Override
    public User updateUser(User user, String userEmail) throws UserNotFoundException
    {
        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }

        return userRepository.save(user);
    }



//    @Override
//    public List<User> getAllUser() {
//        return (List<User>) userRepository.findAll(); //findAll return type is iterable
//    }
}
