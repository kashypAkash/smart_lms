package com.lms.cmpe.dao;

import com.lms.cmpe.model.Phone;
import com.lms.cmpe.model.Book;
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
 * Created by Nischith on 11/27/2016.
 */

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> getAllBooks() {
        //open a hibernate session
        Session session = sessionFactory.openSession();

        // Get all the Books
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        criteriaQuery.from(Book.class);
        List<Book> books = session.createQuery(criteriaQuery).getResultList();

        //Close the session
        session.close();
        return books;
    }

    @Override
    public Book getBookById(int id) {

        //Open the session
        Session session = sessionFactory.openSession();

        // Get the Book by Id
        Book book = null;
        try{
            book = session.get(Book.class,id);
        }
        catch (NoResultException e){
            System.out.println(e.getMessage());
        }
        session.close();
        return book;
    }

    @Override
    public List<Book> getBooksByKeyword(String keyword) {

        Session session = sessionFactory.openSession();
        List<Book> booksList = new ArrayList<>();

        Query query = session.createQuery("from keyword where bookkeywords = :keyword");
        query.setParameter("keyword",keyword);
        booksList = query.getResultList();

        session.close();
        return booksList;
    }

    @Override
    public void addBook(Book book) {
        // todo: implement transactions
        // Open a session
        Session session = sessionFactory.openSession();

        // begin a transaction
        session.beginTransaction();

        session.save(book);

        // add the book & commit trasaction

        session.getTransaction().commit();

        //close the session
        session.close();

    }

    @Override
    public void deleteBook(Book book) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.delete(book);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public void updateBook(Book book) {

        Session session = sessionFactory.openSession();
        // begin a transaction
        session.beginTransaction();
        // update and commit transaction
        try {
            session.update(book);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            session.getTransaction().rollback();
        }
        //close the session
        session.close();
    }
}
