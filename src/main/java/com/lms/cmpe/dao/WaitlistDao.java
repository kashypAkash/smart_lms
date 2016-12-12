package com.lms.cmpe.dao;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.User;
import com.lms.cmpe.model.Waitlist;

import java.util.List;

/**
 * Created by Nischith on 11/27/2016.
 */
public interface WaitlistDao {
    boolean createWaitlist(Waitlist waitlist);
    List<Book> getBooks(User user);
}
