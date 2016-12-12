package com.lms.cmpe.service;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.User;

import java.util.List;

/**
 * Created by Chinmay on 11-12-2016.
 */
public interface WaitlistBooksToBeAssignedService {

     List<Book> getWaitlistedBooks(User user);
}
