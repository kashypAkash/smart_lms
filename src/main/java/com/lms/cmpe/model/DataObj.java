package com.lms.cmpe.model;

import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * Created by akash on 11/17/16.
 */
@ComponentScan
public class DataObj {
    private Phone phone;
    private List<User> user;

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
