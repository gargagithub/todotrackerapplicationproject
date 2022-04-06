package com.example.userauthentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String userEmail;
    private String firstName;
    private String lastName;
    private String password;
    private String mobileNo;

}

