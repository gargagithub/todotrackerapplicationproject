package com.niit.ToDoService;

import com.niit.ToDoService.domain.BasedOnPriority;
import com.niit.ToDoService.domain.Task;
import com.niit.ToDoService.domain.User;
import com.niit.ToDoService.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User user;
    private Task task;
    @BeforeEach
    public void setUp() {
         user = new User();
         task = new Task();
        user.setUserEmail("saisandeep01@gmail.com");
        user.setFirstName("saisandeep");
        user.setLastName("srirangapuram");
        user.setPassword("sandeep123");
        user.setMobileNo("7330843717");

        task.setTaskId(1);
        task.setTaskTitle("testing");
        task.setTaskStatus("active");
        task.setTaskDescription("doing positive and negative test cases");
        task.setTaskPriority(BasedOnPriority.LOW);

    }

    @AfterEach
    public void tearDown() {
        user = null;
        task = null;
        userRepository.deleteAll();

    }

    @Test
    public void saveUser(){
        userRepository.insert(user);
        User user1=userRepository.findById(user.getUserEmail()).get();
        assertNotNull(user1);
        assertEquals(user.getUserEmail(),user1.getUserEmail());
    }

    @Test
    public void deleteUser(){
        userRepository.insert(user);
        User user1=userRepository.findById(user.getUserEmail()).get();
        userRepository.delete(user1);
        assertEquals(Optional.empty(),userRepository.findById(user.getUserEmail()));
    }

    @Test
    public  void getAllUsers(){

        userRepository.insert(user);
        List<User> list=userRepository.findAll();
        assertEquals(1,list.size());
        assertEquals("saisandeep01@gmail.com",list.get(0).getUserEmail());
    }

    @Test
    public void updateUser(){
        userRepository.insert(user);
        user.setFirstName("sai");
        assertEquals("sai",user.getFirstName());
    }
}
