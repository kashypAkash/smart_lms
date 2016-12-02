package com.lms.cmpe.dao;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.BookKeywords;
import com.lms.cmpe.model.Transaction;
import com.lms.cmpe.model.User;
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

/**
 * Created by Nischith on 11/28/2016.
 */
@Repository
public class TransactionDaoImpl implements TransactionDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public boolean checkoutBooks(Transaction transaction) {
      /*  Session session = sessionFactory.openSession();
        // Get the User by Id
        User user = null;
        try{
            user = session.get(User.class,1);
        }
        catch (NoResultException e){
            System.out.println(e.getMessage());
        }
        List<Transaction> transactions = new ArrayList<Transaction>();

        String s = "from Transaction t where t.user=:user";
        System.out.println(s);
        Query query = session.createQuery(s);
        query.setParameter("user",user);
        transactions = query.getResultList();*/

        Session session = sessionFactory.openSession();
        String retrieve = "SELECT * FROM TransactionBooks tb join TransactionBooks.transaction t where " +
                "tb.transactionBooksId = t.transactionId and t.transactionDate > SYSDATE and t.userId = :userId "+
                "and tb.returnDate is null";

        session.close();
        return false;
    }

    @Override
    public boolean returnBooks(Transaction transaction) {
        return false;
    }

    @Override
    public List<Book> getCheckedOutBooksByUser(int userId){

        Session session = sessionFactory.openSession();
        String retrieve = "SELECT tb.transactionBooksId FROM TransactionBooks tb join tb.transaction t where " +
                "tb.transactionBooksId = t.transactionId and t.user.userId = :userId ";
           //     "tb.transactionBooksId = t.transactionId and t.transactionDate = sysdate() and t.user.userId = :userId "+
           //     "and tb.returnDate is null";
        Query retrieveQuery = (Query) session.createQuery(retrieve);
        retrieveQuery.setParameter("userId",userId);
        int count =(Integer)retrieveQuery.getSingleResult();
        System.out.println("Inside queryyyyyyyyyyyyyy " + count);
        session.close();
        return null;
    }

}
