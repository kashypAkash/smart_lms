package com.lms.cmpe.service;

import com.lms.cmpe.model.Book;

import java.util.List;

/**
 * Created by Nischith on 11/29/2016.
 */
public interface TransactionService {
    List<Book> getCheckedOutBooksByUser(int userId);

}
