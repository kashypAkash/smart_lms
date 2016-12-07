package com.lms.cmpe.dao;

import com.lms.cmpe.model.Transaction;
import com.lms.cmpe.model.Book;

import java.util.List;

/**
 * Created by Nischith on 11/27/2016.
 */

public interface TransactionDao {
    Transaction checkOutBooks(Transaction transaction, int userId);
    boolean returnBooks(Transaction transaction);
    List<Book> getCheckedOutBooksByUser(int userId);
}
