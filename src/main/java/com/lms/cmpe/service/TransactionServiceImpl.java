package com.lms.cmpe.service;

import com.lms.cmpe.dao.BookDao;
import com.lms.cmpe.dao.TransactionDao;
import com.lms.cmpe.model.Book;
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
    public List<Book> getCheckedOutBooksByUser(int userId) { return transactionDao.getCheckedOutBooksByUser(userId); }

}
