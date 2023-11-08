package com.example.demo.service;

import com.example.demo.model.entity.User;
import com.example.demo.model.request.LoginRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;


public interface UserserviceI {
    User findByEmail (String maStudent);
    void signin(LoginRequest userSign);
    User getUserLogin(HttpSession session);

    List<User> getUsersByRoleName(String roleName);
    User findById(Integer id);

    List<User> findAll();

}
