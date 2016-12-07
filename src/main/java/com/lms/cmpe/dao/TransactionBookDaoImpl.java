package com.lms.cmpe.dao;

import com.lms.cmpe.model.TransactionBooks;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

/**
 * Created by akash on 12/7/16.
 */

@Repository
public class TransactionBookDaoImpl implements TransactionBooksDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public TransactionBooks getTransactionBookById(int id) {

            Session session = sessionFactory.openSession();

            // Get the TransactionBooks by Id
            TransactionBooks transactionBook = null;
            try{
                transactionBook = session.get(TransactionBooks.class,id);
            }
            catch (NoResultException e){
                System.out.println(e.getMessage());
            }

            session.close();
            return transactionBook;

    }
}
