package com.lms.cmpe.service;

import com.lms.cmpe.model.Phone;
import com.lms.cmpe.model.User;

import java.util.List;

/**
 * Created by akash on 11/13/16.
 */
public interface UserService {
    List<User> getUsers();
    User getUserById(int id);
    boolean saveUser(User user);
    void deleteUser(User user);
    void updateUser(User user);
    List<Phone> getUsersByNumber(String numbers);
    User getUserByEmail(String email);
}
