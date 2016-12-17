package com.lms.cmpe.dao;

import com.lms.cmpe.model.User;

import java.util.List;

/**
 * Created by akash on 11/13/16.
 */
public interface UserDao {
    List<User> getUsers();
    User getUserById(int id);

    boolean saveUser(User user);
    void deleteUser(User user);
    void updateUser(User user);
    User getUserByEmail(String email);

}
