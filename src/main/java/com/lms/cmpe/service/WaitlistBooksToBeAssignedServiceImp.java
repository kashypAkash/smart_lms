package com.lms.cmpe.service;

import com.lms.cmpe.dao.WaitlistBooksToBeAssignedDao;
import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chinmay on 11-12-2016.
 */

@Service
public class WaitlistBooksToBeAssignedServiceImp implements WaitlistBooksToBeAssignedService {

    @Autowired
    private WaitlistBooksToBeAssignedDao waitlistBooksToBeAssignedDao;

    public List<Book> getWaitlistedBooks(User user)
    {
        return waitlistBooksToBeAssignedDao.getWaitlistedBooks(user);
    }
}
