package com.lms.cmpe.dao;

import com.lms.cmpe.model.*;
import com.lms.cmpe.service.LmsException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.orm.hibernate3.SessionFactoryUtils.getSession;


/**
 * Created by Nischith on 11/28/2016.
 */
@Repository
public class TransactionDaoImpl implements TransactionDao{

    @Autowired
    private SessionFactory sessionFactory;



    @Override
    @SuppressWarnings("unchecked")
    public Transaction checkOutBooks(Transaction transaction, int userId) {

       try {
           Session session = sessionFactory.openSession();

           session.beginTransaction();
           String retrieve = "select count(*) from TransactionBooks tb join tb.transaction t" +
                   " where tb.returnDate" +
                   " is null and t.user.userId=:userId and day(t.transactionDate)=day(current_date)";
           Query retrieveQuery = (Query) session.createQuery(retrieve);
           retrieveQuery.setParameter("userId", userId);
           //System.out.println((int)retrieveQuery.getResultList().size()+"in transaction query result");
           System.out.println(retrieveQuery.getResultList() + "only today");

           Long todayBooks = (Long) retrieveQuery.getResultList().get(0);
           int temp = transaction.getTransactionBooksList().size();
           if (todayBooks + temp > 5) {
               System.out.println(todayBooks + temp + "the number of bookks today");
               throw new LmsException("maximumNoOfBooksPerDayViolated");
           }
           String r2 = "select count(*) from TransactionBooks tb join tb.transaction t" +
                   " where tb.returnDate" +
                   " is null and t.user.userId=:userId";
           Query q2 = (Query) session.createQuery(r2);
           q2.setParameter("userId", userId);
           System.out.println(q2.getResultList() + "all days");
           Long allBooks = (Long) q2.getResultList().get(0);
           if (allBooks + temp > 10) {
               System.out.println(allBooks + temp + "number of books all time");
               throw new LmsException("maximumNoOfBooksViolated");
           }
           for (TransactionBooks transactionBook:transaction.getTransactionBooksList()) {
               int bookId=transactionBook.getBook().getBookId();
               Book book = session.get(Book.class,bookId);
               System.out.println("number of available copies"+book.getNoOfAvailableCopies());
               book.setNoOfAvailableCopies(book.getNoOfAvailableCopies()-1);
               book.getNoOfAvailableCopies();
               session.update(book);

           }
           session.save(transaction);
           session.getTransaction().commit();
           session.close();
           return transaction;
       }catch (LmsException e){
           System.out.println(e);
           return null;
       }
    }

    @Override
    public boolean returnBooks(Transaction transaction, int userId) {
        return false;
    }

    @Override

    public List<Book> getCheckedOutBooksByUser(int userId){

        Session session = sessionFactory.openSession();
        String retrieve = "SELECT tb.transactionBooksId FROM TransactionBooks tb join tb.transaction t where " +
                "tb.transactionBooksId = t.transactionId and t.user.userId = :userId ";
        //     "and t.transactionDate = sysdate() "+
        //     "and tb.returnDate is null";
        Query retrieveQuery = (Query) session.createQuery(retrieve);
        retrieveQuery.setParameter("userId",userId);
        int count =(Integer)retrieveQuery.getSingleResult();
        System.out.println("Inside queryyyyyyyyyyyyyy " + count);
        session.close();
        return null;
    }

}
