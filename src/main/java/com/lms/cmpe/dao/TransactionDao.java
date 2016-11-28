package com.lms.cmpe.dao;

import com.lms.cmpe.model.Transaction;

/**
 * Created by Nischith on 11/27/2016.
 */
public interface TransactionDao {
    boolean checkoutBooks(Transaction transaction);
    boolean returnBooks(Transaction transaction);
}
