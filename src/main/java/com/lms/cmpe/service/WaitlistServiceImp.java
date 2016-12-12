package com.lms.cmpe.service;

import com.lms.cmpe.dao.WaitlistDao;
import com.lms.cmpe.model.Waitlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Chinmay on 11-12-2016.
 */
@Service
public class WaitlistServiceImp implements WaitlistService {

    @Autowired
    private WaitlistDao waitlistDao;

    @Override
    public boolean storeWaitlist(Waitlist waitlist) {

        return waitlistDao.createWaitlist(waitlist);
    }
}
