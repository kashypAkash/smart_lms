package com.lms.cmpe.dao;

import com.lms.cmpe.model.Transaction;
import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.TransactionBooks;

import java.util.*;

/**
 * Created by Nischith on 11/27/2016.
 */
public interface TransactionDao {
    Transaction checkOutBooks(Transaction transaction, int userId);
    boolean returnBooks(ArrayList<TransactionBooks> transactionBooksList, int userId);
    List<Book> getCheckedOutBooksByUser(int userId);
    List<TransactionBooks> getBooksToBeReturned(int userId);
    void checkForDueDates();
    void checkForWaitlistAssignments();
    boolean reissueBook(int transactionBookId, int userId);
}
