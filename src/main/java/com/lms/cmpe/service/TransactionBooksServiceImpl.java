package com.lms.cmpe.service;

import com.lms.cmpe.dao.TransactionBooksDao;
import com.lms.cmpe.model.TransactionBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash on 12/7/16.
 */

@Service
public class TransactionBooksServiceImpl implements TransactionBooksService {

    @Autowired
    private TransactionBooksDao transactionBooksDao;

    @Override
    public TransactionBooks getTransactionBookById(int id) {
        return transactionBooksDao.getTransactionBookById(id);
    }
}
