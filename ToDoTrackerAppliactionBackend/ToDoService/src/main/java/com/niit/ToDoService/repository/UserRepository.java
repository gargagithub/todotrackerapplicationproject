package com.niit.ToDoService.repository;

import com.niit.ToDoService.domain.Task;
import com.niit.ToDoService.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
}
