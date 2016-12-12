package com.lms.cmpe.service;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.Transaction;
import com.lms.cmpe.model.TransactionBooks;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Nischith on 11/29/2016.
 */
public interface TransactionService {
    Transaction checkOutBooks(Transaction transaction, int userId, Date appTime);

    boolean returnBooks(ArrayList<TransactionBooks> transactionBooksList, int userId,Date appTime);

    List<Book> getCheckedOutBooksByUser(int userId);
    List<TransactionBooks> getBooksToBeReturned(int userId);

    int reissueBook(int transactionBookId, int userId);
}
