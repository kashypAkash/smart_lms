package com.lms.cmpe.dao;

import com.lms.cmpe.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by akash on 11/13/16.
 */

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        //open a hibernate session
        Session session = sessionFactory.openSession();

        // Get all the users
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        criteriaQuery.from(User.class);
        List<User> users = session.createQuery(criteriaQuery).getResultList();

        //Close the session
        session.close();
        return users;
    }

    @Override
    public User getUserById(int id) {

        //Open the session
        Session session = sessionFactory.openSession();

        // Get the User by Id
        User user = null;
        try{
             user = session.get(User.class,id);
        }
        catch (NoResultException e){
            System.out.println(e.getMessage());
        }

        return user;

    }


    @Override
    public boolean saveUser(User user) {

        try {
            // Open a session
            Session session = sessionFactory.openSession();

            // begin a transaction
            session.beginTransaction();

            session.save(user);

            // save the user & commit trasaction

            session.getTransaction().commit();

            //close the session
            session.close();
            return true;
        }catch (Exception e){
            System.out.println(e+"printing exception");
            return false;
        }
    }

    @Override
    public void deleteUser(User user) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.delete(user);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public void updateUser(User user) {

        Session session = sessionFactory.openSession();
        // begin a transaction
        session.beginTransaction();
        // update and commit transaction
        try {
            session.update(user);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            session.getTransaction().rollback();
        }
        //close the session
        session.close();

    }

    @Override
    public User getUserByEmail(String email) {
        Session session = sessionFactory.openSession();
        User user;
        //noinspection JpaQlInspection
        Query query = session.createQuery("from User where email=:email");
        query.setParameter("email",email);
        try {
           user = (User) query.getSingleResult();
        }
        catch (NoResultException e){
            user = null;
        }
        session.close();
        return user;
    }

}
