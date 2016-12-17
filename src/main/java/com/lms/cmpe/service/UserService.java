package com.lms.cmpe.service;

import com.lms.cmpe.model.User;

import java.util.List;

/**
 * Created by akash on 11/13/16.
 */
public interface UserService {
    List<User> getUsers();
    User getUserById(int id);
    int saveUser(User user);
    void deleteUser(User user);
    void updateUser(User user);
    User getUserByEmail(String email);
}
