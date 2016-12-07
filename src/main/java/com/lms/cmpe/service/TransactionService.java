package com.lms.cmpe.service;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.Transaction;

import java.util.List;

/**
 * Created by Nischith on 11/29/2016.
 */
public interface TransactionService {
    Transaction checkOutBooks(Transaction transaction, int userId);
    List<Book> getCheckedOutBooksByUser(int userId);

}
