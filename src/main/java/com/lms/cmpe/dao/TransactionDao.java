package com.lms.cmpe.dao;

import com.lms.cmpe.model.Transaction;
import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.TransactionBooks;

import java.util.*;

/**
 * Created by Nischith on 11/27/2016.
 */
public interface TransactionDao {
    Transaction checkOutBooks(Transaction transaction, int userId,Date appTime);
    boolean returnBooks(ArrayList<TransactionBooks> transactionBooksList, int userId, Date appTime);
    List<Book> getCheckedOutBooksByUser(int userId);
    List<TransactionBooks> getBooksToBeReturned(int userId);
    void checkForDueDates();
    void checkForWaitlistAssignments();
    int reissueBook(int transactionBookId, int userId);
}
