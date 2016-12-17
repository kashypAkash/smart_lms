package com.lms.cmpe.dao;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.User;
import com.lms.cmpe.model.Waitlist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Chinmay on 11-12-2016.
 */
@Repository
public class WaitlistDaoImp implements WaitlistDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean createWaitlist(Waitlist waitlist)
    {
        // Open a session
        Session session = sessionFactory.openSession();
        // begin a transaction
        session.beginTransaction();
        String retrieve="select count(*) from Waitlist w where w.book.bookId=:bookId and w.user.userId=:userId";
        Query retrieveQuery = (Query) session.createQuery(retrieve);
        retrieveQuery.setParameter("bookId", waitlist.getBook().getBookId());
        retrieveQuery.setParameter("userId",waitlist.getUser().getUserId());
        //System.out.println((int)retrieveQuery.getResultList().size()+"in transaction query result");
        System.out.println(retrieveQuery.getResultList() + "only today");

        Long count = (Long) retrieveQuery.getResultList().get(0);
        if(count>0){
            return false;
        }
        session.save(waitlist);
        session.getTransaction().commit();

        //close the session
        session.close();
        return true;
    }

    public List<Book> getBooks(User user)
    {
        // Open a session
        Session session = sessionFactory.openSession();
        int userId = user.getUserId();
        System.out.println("User id in dao is --- "+userId);

        String retrieve="select wt.book from Waitlist wt where wt.user.userId=:userId";
        Query query = (Query) session.createQuery(retrieve);
        query.setParameter("userId", userId);
        List<Book> listbooks = (List<Book>)query.getResultList();
        session.close();
        return listbooks;
    }
}
