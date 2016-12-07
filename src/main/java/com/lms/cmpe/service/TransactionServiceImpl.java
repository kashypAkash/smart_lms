package com.lms.cmpe.service;

import com.lms.cmpe.dao.TransactionDao;
import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nischith on 11/29/2016.
 */
@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public Transaction checkOutBooks(Transaction transaction, int userId) { return transactionDao.checkOutBooks(transaction, userId); }

    @Override
    public List<Book> getCheckedOutBooksByUser(int userId) { return transactionDao.getCheckedOutBooksByUser(userId); }

}
