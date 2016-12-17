package com.lms.cmpe.dao;

import com.lms.cmpe.model.User;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
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
    public int saveUser(User user) {

        try {
            // Open a session
            Session session = sessionFactory.openSession();
            int emailExistsCount = 0;
            int emailRoleAndUniversityIdExistsCount = 0;
            String checkEmailExists = "select u.userId from User u where u.email = :email)";

            Query checkEmailExistsQuery = (Query) session.createQuery(checkEmailExists);
            checkEmailExistsQuery.setParameter("email",user.getEmail());
            try {
                emailExistsCount = (Integer) checkEmailExistsQuery.getSingleResult();
            }catch (NoResultException noResultException)
            {
                System.out.println("checkEmailExistsQuery  existssssssssssssssssssssssssssssss");
            }

            String checkEmailRoleAndUniversityIdExists = "select u.userId from User u where u.universityId = :universityId and u.userRole = :userRole)";

            Query checkEmailRoleAndUniversityIdExistsQuery = (Query) session.createQuery(checkEmailRoleAndUniversityIdExists);
            //checkEmailRoleAndUniversityIdExistsQuery.setParameter("email",user.getEmail());
            checkEmailRoleAndUniversityIdExistsQuery.setParameter("universityId",user.getUniversityId());
            checkEmailRoleAndUniversityIdExistsQuery.setParameter("userRole",user.getUserRole());

            try {
                emailRoleAndUniversityIdExistsCount = (Integer) checkEmailRoleAndUniversityIdExistsQuery.getSingleResult();
            }catch (NoResultException noResultException)
            {
                System.out.println("checkEmailRoleAndUniversityIdExistsQuery Doesnot  existssssssssssssssssssssssssssssss");
            }

            if(emailExistsCount > 0)
            {
                return 1;
            }

            if(emailRoleAndUniversityIdExistsCount > 0)
            {
                return 2;
            }
            else
            {
                System.out.println("Else Doesnot  existssssssssssssssssssssssssssssss");
                // begin a transaction
                session.beginTransaction();
                session.save(user);

                // save the user & commit trasaction
                session.getTransaction().commit();
            }
            //close the session
            session.close();
            return 0;
        }catch (Exception e){
            System.out.println(e+"printing exception");
            return 3;
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
