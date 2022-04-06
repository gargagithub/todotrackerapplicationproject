package com.niit.ToDoService.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data // for no default constructor,prametarized constructor,getters and setters,toString
@Document // for creating document in mongodb
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String userEmail;
    private String firstName;
    private String lastName;
    private String password;
    private String mobileNo;
    private List<Task> officialTask;
    private List<Task> personalTask;
}
