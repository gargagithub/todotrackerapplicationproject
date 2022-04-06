package com.niit.ToDoService;


import com.niit.ToDoService.domain.BasedOnPriority;
import com.niit.ToDoService.domain.Task;
import com.niit.ToDoService.domain.User;
import com.niit.ToDoService.exceptions.TaskAlreadyExistException;
import com.niit.ToDoService.exceptions.TaskNotFoundException;
import com.niit.ToDoService.exceptions.UserAlreadyExistException;
import com.niit.ToDoService.exceptions.UserNotFoundException;
import com.niit.ToDoService.proxy.UserProxy;
import com.niit.ToDoService.repository.UserRepository;
import com.niit.ToDoService.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User user1;
    private List<Task> listOfOfficialTaskForUser1, listOfPersonalTaskForUser1,sortedOfficialTaskForUser1,sortedPersonalTaskForUser1;
    private Task officialTask1, officialTask2, personalTask1, personalTask2;

    @Mock
    private UserProxy userProxy;

    @BeforeEach
    public void setUp() {
        java.util.Date dt = convertionOfdate("2022-03-21");
        officialTask1 = new Task(1, "interviewDate", "active", "most important", BasedOnPriority.MEDIUM, dt);
        officialTask2 = new Task(1, "meeting", "active", "need to attend", BasedOnPriority.HIGH, dt);
        personalTask1 = new Task(1, "cricket", "active", "playtime", BasedOnPriority.LOW, dt);
        personalTask2 = new Task(1, "sleep", "active", "it is bed time", BasedOnPriority.MEDIUM, dt);
        listOfOfficialTaskForUser1 = new ArrayList<>(Arrays.asList(officialTask1, officialTask2));
        listOfPersonalTaskForUser1 = new ArrayList<>(Arrays.asList(personalTask1, personalTask2));
        sortedOfficialTaskForUser1 = new ArrayList<>(Arrays.asList(officialTask2, officialTask1));
        sortedPersonalTaskForUser1 = new ArrayList<>(Arrays.asList(personalTask2, personalTask1));
        user1 = new User("saisandeep@gmail.com", "saisandeep", "srirangapuram", "sai123", "7330843717", null, null);
    }

    @AfterEach
    public void tearDown() {
        user1 = null;
        listOfOfficialTaskForUser1 = null;
        listOfPersonalTaskForUser1 = null;
    }

    @Test
    public void registerUserPositiveTestCase() throws UserAlreadyExistException {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(null));
        when(userRepository.save(user1)).thenReturn(user1);
        assertEquals(user1, userServiceImpl.registerNewUser(user1));
        verify(userRepository, times(1)).findById(user1.getUserEmail());
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public void registerUserNegativeTestCase() {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        assertThrows(UserAlreadyExistException.class, () -> userServiceImpl.registerNewUser(user1));
        verify(userRepository, times(1)).findById(user1.getUserEmail());
        verify(userRepository, times(0)).save(user1);
    }

    @Test
    public void saveOfficialTaskPositiveTestCase() throws UserNotFoundException {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        when(userRepository.save(user1)).thenReturn(user1);
        assertEquals(user1, userServiceImpl.saveOfficialTask("saisandeep@gmail.com", officialTask1));
        verify(userRepository, times(2)).findById(user1.getUserEmail());
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public void saveOfficialTaskNegativeTestCase() {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.saveOfficialTask("saisandeep@gmail.com", officialTask2));
        verify(userRepository, times(1)).findById(user1.getUserEmail());
        verify(userRepository, times(0)).save(user1);
    }

    @Test
    public void savePersonalTaskPositiveTestCase() throws UserNotFoundException {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        when(userRepository.save(user1)).thenReturn(user1);
        assertEquals(user1, userServiceImpl.savePersonalTask("saisandeep@gmail.com", personalTask1));
        verify(userRepository, times(2)).findById(user1.getUserEmail());
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public void savePersonalTaskNegativeTestCase() {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.savePersonalTask("saisandeep@gmail.com", personalTask2));
        verify(userRepository, times(1)).findById(user1.getUserEmail());
        verify(userRepository, times(0)).save(user1);
    }

    @Test
    public void deleteOfficialTaskPositiveTestCase() throws UserNotFoundException, TaskNotFoundException {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        when(userRepository.save(user1)).thenReturn(user1);
        user1.setOfficialTask(listOfOfficialTaskForUser1);
        assertEquals(true, userServiceImpl.deleteOfficialTask("saisandeep@gmail.com", 1));
        verify(userRepository, times(2)).findById(user1.getUserEmail());
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public void deleteOfficialTaskNegativeTestCase() {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.deleteOfficialTask("saisandeep@gmail.com", 1));
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        user1.setOfficialTask(listOfOfficialTaskForUser1);
        assertThrows(TaskNotFoundException.class, () -> userServiceImpl.deleteOfficialTask("saisandeep@gmail.com", 2));
        verify(userRepository, times(3)).findById(user1.getUserEmail());
        verify(userRepository, times(0)).save(user1);
    }

    @Test
    public void deletePersonalTaskPositiveTestCase() throws UserNotFoundException, TaskNotFoundException {
        user1.setPersonalTask(listOfPersonalTaskForUser1);
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        when(userRepository.save(user1)).thenReturn(user1);
        assertEquals(true, userServiceImpl.deletePersonalTask("saisandeep@gmail.com", 1));
        verify(userRepository, times(2)).findById(user1.getUserEmail());
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public void deletePersonalTaskNegativeTestCase() {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.deletePersonalTask("saisandeep@gmail.com", 1));
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        user1.setPersonalTask(listOfPersonalTaskForUser1);
        assertThrows(TaskNotFoundException.class, () -> userServiceImpl.deletePersonalTask("saisandeep@gmail.com", 2));
        verify(userRepository, times(3)).findById(user1.getUserEmail());
        verify(userRepository, times(0)).save(user1);
    }

    @Test
    public void updateOfficialTaskPositiveTestCase() throws UserNotFoundException {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        when(userRepository.save(user1)).thenReturn(user1);
        user1.setOfficialTask(listOfOfficialTaskForUser1);
        assertEquals(user1, userServiceImpl.updateOfficialTask(user1.getUserEmail(), officialTask1,1));
        verify(userRepository, times(2)).findById(user1.getUserEmail());
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public void updateOfficialTaskNegativeTestCase() {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.updateOfficialTask("saisandeep@gmail.com", officialTask1,1));
        verify(userRepository, times(1)).findById(user1.getUserEmail());
        verify(userRepository, times(0)).save(user1);
    }

    @Test
    public void updatePersonalTaskPositiveTestCase() throws UserNotFoundException {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        when(userRepository.save(user1)).thenReturn(user1);
        user1.setPersonalTask(listOfPersonalTaskForUser1);
        assertEquals(user1, userServiceImpl.updatePersonalTask("saisandeep@gmail.com", personalTask1,1));
        verify(userRepository, times(2)).findById(user1.getUserEmail());
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public void updatePersonalTaskNegativeTestCase() {
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.updatePersonalTask(user1.getUserEmail(), personalTask1,1));
        verify(userRepository, times(1)).findById(user1.getUserEmail());
        verify(userRepository, times(0)).save(user1);
    }

    @Test
    public void getAllOfficialTasksPositiveTestCase() throws UserNotFoundException {
        user1.setOfficialTask(listOfOfficialTaskForUser1);
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        assertEquals(listOfOfficialTaskForUser1, userServiceImpl.getAllOfficialTasks("saisandeep@gmail.com"));
        verify(userRepository, times(2)).findById(user1.getUserEmail());
    }

    @Test
    public void getAllOfficialTasksNegativeTestCase() {

        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getAllOfficialTasks("saisandeep@gmail.com"));
        verify(userRepository, times(1)).findById(user1.getUserEmail());
    }

    @Test
    public void getAllPersonalTasksPositiveTestCase() throws UserNotFoundException {
        user1.setPersonalTask(listOfPersonalTaskForUser1);
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        assertEquals(listOfPersonalTaskForUser1, userServiceImpl.getAllPersonalTasks("saisandeep@gmail.com"));
        verify(userRepository, times(2)).findById(user1.getUserEmail());
    }

    @Test
    public void getAllPersonalTasksNegativeTestCase() {

        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getAllPersonalTasks("saisandeep@gmail.com"));
        verify(userRepository, times(1)).findById(user1.getUserEmail());
    }

    @Test
    public void sortedOfficialTaskPositiveTestCase() throws UserNotFoundException{
        user1.setOfficialTask(listOfOfficialTaskForUser1);
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        assertEquals(listOfOfficialTaskForUser1, userServiceImpl.getAllOfficialTasks("saisandeep@gmail.com"));
        verify(userRepository, times(2)).findById(user1.getUserEmail());
        assertEquals(sortedOfficialTaskForUser1, userServiceImpl.sortByPriorityOfOfficialTask("saisandeep@gmail.com"));

    }

    @Test
    public void sortedOfficialTaskNegativeTestCase() throws UserNotFoundException{

        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.sortByPriorityOfOfficialTask("saisandeep@gmail.com"));
        verify(userRepository, times(1)).findById(user1.getUserEmail());
    }

    @Test
    public void sortedPersonalTaskPositiveTestCase() throws UserNotFoundException{
        user1.setPersonalTask(listOfPersonalTaskForUser1);
        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(user1));
        assertEquals(listOfPersonalTaskForUser1, userServiceImpl.getAllPersonalTasks("saisandeep@gmail.com"));
        verify(userRepository, times(2)).findById(user1.getUserEmail());
        assertEquals(sortedPersonalTaskForUser1, userServiceImpl.sortByPriorityOfPersonalTask("saisandeep@gmail.com"));

    }

    @Test
    public void sortedPersonalTaskNegativeTestCase() throws UserNotFoundException{

        when(userRepository.findById(user1.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.sortByPriorityOfPersonalTask("saisandeep@gmail.com"));
        verify(userRepository, times(1)).findById(user1.getUserEmail());
    }
    public Date convertionOfdate(String date) {
        java.util.Date dt = null;
        try {
            dt = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            System.out.println(e);
        }
        return dt;
    }
}
