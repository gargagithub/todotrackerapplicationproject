package com.example.userauthentication;

import com.example.userauthentication.controller.UserController;
import com.example.userauthentication.exception.UserAlreadyExistException;
import com.example.userauthentication.exception.UserNotFoundException;
import com.example.userauthentication.model.User;
import com.example.userauthentication.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user1;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        user1=new User("saisandeep1@gmail.com","saisandeep","srirangapuram","saisandeep123","7330843717");

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    public void tearDown() {
        user1 = null;
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

    @Test
    public void saveMethodPositiveCase() throws Exception {
        when(userService.saveUser(any())).thenReturn(user1);
        mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isCreated())
                        .andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).saveUser(any());
    }

    @Test
    public void saveMethodNegativeTestCase() throws Exception {
            when(userService.saveUser(any())).thenThrow(UserAlreadyExistException.class);
            mockMvc.perform(post("/api/v1/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(user1)))
                            .andExpect(status().isConflict())
                            .andDo(MockMvcResultHandlers.print());
            verify(userService, times(1)).saveUser(any());
    }
    @Test
    public void loginMethodPositiveTestCase() throws Exception {
        when(userService.findByUserEmailAndPassword(user1.getUserEmail(),user1.getPassword())).thenReturn(user1);
        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isInternalServerError())
                        .andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).findByUserEmailAndPassword(user1.getUserEmail(),user1.getPassword());
    }

    @Test
    public void loginMethodNegativeTestCase() throws Exception {
        when(userService.findByUserEmailAndPassword(user1.getUserEmail(),user1.getPassword())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).findByUserEmailAndPassword(user1.getUserEmail(),user1.getPassword());
    }

    @Test
    public void updateMethodPositiveCase() throws Exception {
        when(userService.updateUser(any(),anyString())).thenReturn(user1);
        mockMvc.perform(put("/api/v1/userUpdate/saisandeep1@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isOk())
                        .andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).updateUser(any(),anyString());
    }

    @Test
    public void updateMethodNegativeTestCase() throws Exception {
        when(userService.updateUser(any(),anyString())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(put("/api/v1/userUpdate/saisandeep12@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                        .andExpect(status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).updateUser(any(),anyString());
    }
}
