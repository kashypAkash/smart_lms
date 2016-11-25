package com.lms.cmpe.service;

import com.lms.cmpe.dao.PhoneDao;
import com.lms.cmpe.model.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash on 11/16/16.
 */
@Service
public class PhoneServiceImpl implements PhoneService{

    @Autowired
    private PhoneDao phoneDao;

    @Override
    public void createPhone(Phone phone) {
        phoneDao.createPhone(phone);
    }

    @Override
    public void updatePhone(Phone phone) { phoneDao.updatePhone(phone);   }

    @Override
    public void deletePhone(Phone phone) {
        phoneDao.deletePhone(phone);
    }

    @Override
    public Phone getPhoneById(int id) {
        return phoneDao.getPhoneById(id);
    }
}
