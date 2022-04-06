package com.example.userauthentication.service;

import com.example.userauthentication.model.User;

import java.util.Map;

public interface SecurityTokenGenerator {

    Map<String,String> generateToken(User user);
}
