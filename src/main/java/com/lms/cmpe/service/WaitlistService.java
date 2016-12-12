package com.lms.cmpe.service;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.User;
import com.lms.cmpe.model.Waitlist;

import java.util.List;

/**
 * Created by Chinmay on 11-12-2016.
 */
public interface WaitlistService {


    boolean storeWaitlist(Waitlist waitlist);
    List<Book> getWaitlistBooks(User user);

}
