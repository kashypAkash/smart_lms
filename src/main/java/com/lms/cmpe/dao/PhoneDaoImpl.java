package com.lms.cmpe.dao;

import com.lms.cmpe.model.Phone;
import com.lms.cmpe.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by akash on 11/16/16.
 */

@Repository
public class PhoneDaoImpl implements PhoneDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void createPhone(Phone phone) {
        // Open a session
        Session session = sessionFactory.openSession();

        // begin a transaction
        session.beginTransaction();

        // save the user & commit trasaction
        session.save(phone);
        session.getTransaction().commit();

        //close the session
        session.close();
    }

    @Override
    public void updatePhone(Phone phone) {
        Session session = sessionFactory.openSession();
        // begin a transaction
        session.beginTransaction();
        // update and commit transaction
        try {
            session.update(phone);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            session.getTransaction().rollback();
        }
        //close the session
        session.close();

    }

    @Override
    public void deletePhone(Phone phone) {
        Session session = sessionFactory.openSession();
        // begin a transaction
        session.beginTransaction();
        // update and commit transaction
        try {
            session.delete(phone);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            session.getTransaction().rollback();
        }
        //close the session
        session.close();
    }

    @Override
    public Phone getPhoneById(int id) {
        Session session = sessionFactory.openSession();

        // Get the User by Id
        Phone phone = null;
        try{
            phone = session.get(Phone.class,id);
        }
        catch (NoResultException e){
            System.out.println(e.getMessage());
        }

        return phone;
    }

    public List<User> getPhoneUsers(int id){

        return null;
    }
}
