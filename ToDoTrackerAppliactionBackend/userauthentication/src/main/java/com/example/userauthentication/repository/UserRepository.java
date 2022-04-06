package com.example.userauthentication.repository;

import com.example.userauthentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//HERE WE USE THE USER CLASS NAME AND PRIMARY KEY OF DATA TYPE(DOMAIN ENTITY,DOMAIN ENTITY ID)
public interface UserRepository extends JpaRepository<User,String> {

     User findByUserEmailAndPassword(String userEmail, String passWord);

//     void insert(User user);
}
