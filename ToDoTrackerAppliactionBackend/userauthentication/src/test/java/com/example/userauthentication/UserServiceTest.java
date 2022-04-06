package com.example.userauthentication;

import com.example.userauthentication.exception.UserAlreadyExistException;
import com.example.userauthentication.exception.UserNotFoundException;
import com.example.userauthentication.model.User;
import com.example.userauthentication.repository.UserRepository;
import com.example.userauthentication.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    private User user1;


    @BeforeEach
    public void setUp(){
        user1=new User("saisandeep1@gmail.com","saisandeep","srirangapuram","saisandeep123","7330843717");
    }
    @AfterEach
    public void tearDown(){
        user1=null;

    }

    @Test
    public void saveUserPositiveTestCase() throws UserAlreadyExistException
    {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(null));
        when(userRepository.save(user1)).thenReturn(user1);
        assertEquals(user1, userServiceImpl.saveUser(user1));
        verify(userRepository,times(1)).findById(user1.getUserEmail());
        verify(userRepository,times(1)).save(user1);
    }


    @Test
    public void saveUserNegativeTestCase()
    {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        assertThrows(UserAlreadyExistException.class,()->userServiceImpl.saveUser(user1));
        verify(userRepository,times(1)).findById(user1.getUserEmail());
        verify(userRepository,times(0)).save(user1);
    }

    @Test
    public void findByEmailIdAndPasswordPositiveTestCase() throws UserNotFoundException
    {
        when(userRepository.findByUserEmailAndPassword(user1.getUserEmail(),user1.getPassword())).thenReturn(user1);
        assertEquals(user1, userServiceImpl.findByUserEmailAndPassword(user1.getUserEmail(),user1.getPassword()));
        verify(userRepository,times(1)).findByUserEmailAndPassword(user1.getUserEmail(),user1.getPassword());
    }

    @Test
    public void findByEmailIdAndPasswordNegativeTestCase()
    {
        when(userRepository.findByUserEmailAndPassword(user1.getUserEmail(),user1.getPassword())).thenReturn(null);
        assertThrows(UserNotFoundException.class,()->userServiceImpl.findByUserEmailAndPassword(user1.getUserEmail(),user1.getPassword()));
        verify(userRepository,times(1)).findByUserEmailAndPassword(user1.getUserEmail(),user1.getPassword());
    }

    @Test
    public void updateUserPositiveTestCase() throws UserNotFoundException
    {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        when(userRepository.save(user1)).thenReturn(user1);
        assertEquals(user1, userServiceImpl.updateUser(user1,user1.getUserEmail()));
        verify(userRepository,times(1)).findById(user1.getUserEmail());
        verify(userRepository,times(1)).save(user1);
    }

    @Test
    public void updateUserNegativeTestCase()
    {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class,()->userServiceImpl.updateUser(user1,user1.getUserEmail()));
        verify(userRepository,times(1)).findById(user1.getUserEmail());
        verify(userRepository,times(0)).save(user1);
    }
}