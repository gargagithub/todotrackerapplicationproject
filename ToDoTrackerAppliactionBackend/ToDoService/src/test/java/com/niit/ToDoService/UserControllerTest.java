package com.niit.ToDoService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.ToDoService.controller.UserController;
import com.niit.ToDoService.domain.BasedOnPriority;
import com.niit.ToDoService.domain.DbSequence;
import com.niit.ToDoService.domain.Task;
import com.niit.ToDoService.domain.User;
import com.niit.ToDoService.exceptions.TaskNotFoundException;
import com.niit.ToDoService.exceptions.UserAlreadyExistException;
import com.niit.ToDoService.exceptions.UserNotFoundException;
import com.niit.ToDoService.repository.UserRepository;
import com.niit.ToDoService.service.SequenceGeneratorService;
import com.niit.ToDoService.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private UserController userController;

    private User user1;
    private List<Task> listOfOfficialTaskForUser1, listOfPersonalTaskForUser1,sortedOfficialTaskForUser1,sortedPersonalTaskForUser1;
    private Task officialTask1, officialTask2, personalTask1, personalTask2;
    private DbSequence dbSequence;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @BeforeEach
    public void setUp() {
        java.util.Date dt = convertionOfdate("2022-03-21");
        officialTask1 = new Task(1, "interviewDate", "active", "most important", BasedOnPriority.HIGH, dt);
        officialTask2 = new Task(2, "meeting", "active", "need to attend", BasedOnPriority.HIGH, dt);
        personalTask1 = new Task(1, "cricket", "active", "playtime", BasedOnPriority.MEDIUM, dt);
        personalTask2 = new Task(2, "sleep", "active", "it is bed time", BasedOnPriority.LOW, dt);
        listOfOfficialTaskForUser1 = new ArrayList<>(Arrays.asList(officialTask1, officialTask2));
        listOfPersonalTaskForUser1 = new ArrayList<>(Arrays.asList(personalTask1, personalTask2));
        sortedOfficialTaskForUser1 = new ArrayList<>(Arrays.asList(officialTask2, officialTask1));
        sortedPersonalTaskForUser1 = new ArrayList<>(Arrays.asList(personalTask2, personalTask1));
        user1 = new User("saisandeep@gmail.com", "saisandeep", "srirangapuram", "sai123", "7330843717", listOfOfficialTaskForUser1, listOfPersonalTaskForUser1);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    public void tearDown() {
        user1 = null;
        listOfOfficialTaskForUser1 = null;
        listOfPersonalTaskForUser1 = null;
    }

    @Test
    public void userRegistryPositiveTest() throws Exception {
        when(userServiceImpl.registerNewUser(any())).thenReturn(user1);
        mockMvc.perform(post("/api/v2/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isCreated())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).registerNewUser(any());
    }

    @Test
    public void userRegistryNegativeTest() throws Exception {
        when(userServiceImpl.registerNewUser(any())).thenThrow(UserAlreadyExistException.class);
        mockMvc.perform(post("/api/v2/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isConflict())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).registerNewUser(any());
    }

    @Test
    public void saveOfficialTaskPositiveTest() throws Exception {

        when(userServiceImpl.saveOfficialTask(anyString(), any())).thenReturn(user1);
        officialTask1.setTaskId(1);
        mockMvc.perform(post("/api/v2/user/officialTask/saisandeep@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isCreated())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).saveOfficialTask(anyString(), any());
    }

    @Test
    public void saveOfficialTaskNegativeTest() throws Exception {

        when(userServiceImpl.saveOfficialTask(anyString(), any())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(post("/api/v2/user/officialTask/saisandeep@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).saveOfficialTask(anyString(), any());
    }

    @Test
    public void savePersonalTaskPositiveTest() throws Exception {

        when(userServiceImpl.savePersonalTask(anyString(), any())).thenReturn(user1);
        mockMvc.perform(post("/api/v2/user/personalTask/saisandeep@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).savePersonalTask(anyString(), any());
    }

    @Test
    public void savePersonalTaskNegativeTest() throws Exception {

        when(userServiceImpl.savePersonalTask(anyString(), any())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(post("/api/v2/user/personalTask/saisandeep@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).savePersonalTask(anyString(), any());
    }

    @Test
    public void deleteOfficialTaskPositiveTest() throws Exception {

        when(userServiceImpl.deleteOfficialTask(anyString(), anyInt())).thenReturn(true);
        mockMvc.perform(delete("/api/v2/user/officialTask/saisandeep@gmail.com/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isOk())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).deleteOfficialTask(anyString(), anyInt());
    }

    @Test
    public void deleteOfficialTaskNegativeTest() throws Exception {

        when(userServiceImpl.deleteOfficialTask(anyString(), anyInt())).thenThrow(UserNotFoundException.class, TaskNotFoundException.class);
        mockMvc.perform(delete("/api/v2/user/officialTask/saisandeep@gmail.com/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isNotFound())
                         .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).deleteOfficialTask(anyString(), anyInt());
    }

    @Test
    public void deletePersonalTaskPositiveTest() throws Exception {

        when(userServiceImpl.deletePersonalTask(anyString(), anyInt())).thenReturn(true);
        mockMvc.perform(delete("/api/v2/user/personalTask/saisandeep@gmail.com/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isOk())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).deletePersonalTask(anyString(), anyInt());
    }

    @Test
    public void deletePersonalTaskNegativeTest() throws Exception {

        when(userServiceImpl.deletePersonalTask(anyString(), anyInt())).thenThrow(UserNotFoundException.class, TaskNotFoundException.class);
        mockMvc.perform(delete("/api/v2/user/personalTask/saisandeep@gmail.com/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).deletePersonalTask(anyString(), anyInt());
    }

    @Test
    public void updateOfficialTaskPositiveTest() throws Exception {

        when(userServiceImpl.updateOfficialTask(anyString(), any(),anyInt())).thenReturn(user1);
        mockMvc.perform(put("/api/v2/user/officialTask/saisandeep@gmail.com/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isOk())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).updateOfficialTask(anyString(), any(),anyInt());
    }

    @Test
    public void updateOfficialTaskNegativeTest() throws Exception {

        when(userServiceImpl.updateOfficialTask(anyString(), any(),anyInt())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(put("/api/v2/user/officialTask/saisandeep@gmail.com/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).updateOfficialTask(anyString(), any(),anyInt());
    }

    @Test
    public void updatePersonalTaskPositiveTest() throws Exception {

        when(userServiceImpl.updatePersonalTask(anyString(), any(),anyInt())).thenReturn(user1);
        mockMvc.perform(put("/api/v2/user/personalTask/saisandeep@gmail.com/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isOk())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).updatePersonalTask(anyString(), any(),anyInt());
    }

    @Test
    public void updatePersonalTaskNegativeTest() throws Exception {

        when(userServiceImpl.updatePersonalTask(anyString(), any(),anyInt())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(put("/api/v2/user/personalTask/saisandeep@gmail.com/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).updatePersonalTask(anyString(), any(),anyInt());
    }

    @Test
    public void getAllOfficialTaskPositiveTest() throws Exception {

        when(userServiceImpl.getAllOfficialTasks(anyString())).thenReturn(listOfPersonalTaskForUser1);
        mockMvc.perform(get("/api/v2/user/officialTask/saisandeep@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isOk())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).getAllOfficialTasks(anyString());
    }

    @Test
    public void getAllOfficiallTaskNegativeTest() throws Exception {

        when(userServiceImpl.getAllOfficialTasks(anyString())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get("/api/v2/user/officialTask/saisandeep@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).getAllOfficialTasks(anyString());
    }

    @Test
    public void getAllPersonalTaskPositiveTest() throws Exception {

        when(userServiceImpl.getAllPersonalTasks(anyString())).thenReturn(listOfPersonalTaskForUser1);
        mockMvc.perform(get("/api/v2/user/personalTask/saisandeep@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isOk())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).getAllPersonalTasks(anyString());
    }

    @Test
    public void getAllPersonalTaskNegativeTest() throws Exception {

        when(userServiceImpl.getAllPersonalTasks(anyString())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get("/api/v2/user/personalTask/saisandeep@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).getAllPersonalTasks(anyString());
    }

    @Test
    public void sortedOfficialTaskPositiveTest() throws Exception {

        when(userServiceImpl.sortByPriorityOfOfficialTask(anyString())).thenReturn(sortedOfficialTaskForUser1);
        mockMvc.perform(get("/api/v2/user/sortedOfficialTask/saisandeep@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).sortByPriorityOfOfficialTask(anyString());
    }

    @Test
    public void sortedOfficiallTaskNegativeTest() throws Exception {

        when(userServiceImpl.sortByPriorityOfOfficialTask(anyString())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get("/api/v2/user/sortedOfficialTask/saisandeep@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).sortByPriorityOfOfficialTask(anyString());
    }

    @Test
    public void sortedPersonalTaskPositiveTest() throws Exception {

        when(userServiceImpl.sortByPriorityOfPersonalTask(anyString())).thenReturn(sortedPersonalTaskForUser1);
        mockMvc.perform(get("/api/v2/user/sortedPersonalTask/saisandeep@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).sortByPriorityOfPersonalTask(anyString());
    }

    @Test
    public void sortedPersonalTaskNegativeTest() throws Exception {

        when(userServiceImpl.sortByPriorityOfPersonalTask(anyString())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get("/api/v2/user/sortedPersonalTask/saisandeep@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
        verify(userServiceImpl, times(1)).sortByPriorityOfPersonalTask(anyString());
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

    private static String jsonToString(final Object obj) throws JsonProcessingException {
        String result = null;
        try {
            ObjectMapper mapper = new ObjectMapper(); // provides functionality for reading and writing JSON, either to and from POJO
            String jsonContent = mapper.writeValueAsString(obj);
            result = jsonContent;
        } catch (JsonProcessingException e) {
            result = "error while conversion";
        }
        return result;
    }
}
