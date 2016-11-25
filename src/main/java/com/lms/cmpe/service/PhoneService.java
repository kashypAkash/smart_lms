package com.lms.cmpe.service;

import com.lms.cmpe.model.Phone;

/**
 * Created by akash on 11/13/16.
 */
public interface PhoneService {
    void createPhone(Phone phone);
    void updatePhone(Phone phone);
    void deletePhone(Phone phone);
    Phone getPhoneById(int id);
}
