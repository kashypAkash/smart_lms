package com.lms.cmpe.dao;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.User;
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
public class WaitlistedBooksToBeAssignedDaoImp  implements  WaitlistBooksToBeAssignedDao{

    @Autowired
    private SessionFactory sessionFactory;

    public List<Book> getWaitlistedBooks(User user)
    {
        System.out.println("User email in dao is --- "+user.getEmail());
        //Open the session
        Session session = sessionFactory.openSession();
        int userId = user.getUserId();
        System.out.println("User id in dao is --- "+userId);

        String retrieve="select wt.book from WaitlistBooksToBeAssigned wt where wt.user.userId=:userId";
        Query query = (Query) session.createQuery(retrieve);
        query.setParameter("userId", userId);
        List<Book> listbooks = (List<Book>)query.getResultList();
        session.close();
        return listbooks;
    }
}
