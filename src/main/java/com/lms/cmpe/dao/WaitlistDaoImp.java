package com.lms.cmpe.dao;

import com.lms.cmpe.model.Waitlist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

        session.save(waitlist);
        session.getTransaction().commit();

        //close the session
        session.close();
        return true;
    }
}
