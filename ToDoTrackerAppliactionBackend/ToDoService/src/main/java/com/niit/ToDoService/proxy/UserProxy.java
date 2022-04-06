package com.niit.ToDoService.proxy;

import com.niit.ToDoService.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-authentication-service",url = "localhost:9000")//nameAndPort should be same as app properties
public interface UserProxy {
    @PostMapping("api/v1/register")
    public ResponseEntity<?> saveUser(@RequestBody User user);//should be same as the method in auth service
}
