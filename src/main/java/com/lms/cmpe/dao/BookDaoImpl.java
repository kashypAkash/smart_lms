package com.lms.cmpe.dao;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.BookKeywords;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;


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

        /*String str = "1986-04-08 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        ApplicationTime ap = new ApplicationTime(dateTime);*/
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
    public List<BookKeywords> getBooksByKeyword(String keyword) {
        Session session = sessionFactory.openSession();
        List<BookKeywords> bookKeywords = new ArrayList<BookKeywords>();

        String s = "from BookKeywords bk where bk.keyword in (:keyword)";
        System.out.println(s);
        Query query = session.createQuery(s);
        query.setParameter("keyword",keyword);
        bookKeywords = query.getResultList();

        session.close();

        return bookKeywords;
    }

    @Override
    public List<Book> getBooksByKey(String key){
        List<Book> books = new ArrayList<Book>();
        for(BookKeywords keyword:getBooksByKeyword(key)){
            books.add(keyword.getBook());
        }
        return books;
    }

    @Override
    public void addBook(Book book) {

        // Open a session
        Session session = sessionFactory.openSession();

        // begin a transaction
        session.beginTransaction();

        book.setNoOfAvailableCopies(book.getNoOfCopies());
        session.save(book);

        // add the book & commit trasaction

        session.getTransaction().commit();

        //close the session
        session.close();

    }

    @Override
    public boolean deleteBook(Book book) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        String retrieve = "SELECT count(*) FROM TransactionBooks tb where " +
                "tb.returnDate is null and tb.book.bookId=:bookId";
        Query retrieveQuery = (Query) session.createQuery(retrieve);
        retrieveQuery.setParameter("bookId",book.getBookId());
        long count =(long)retrieveQuery.getSingleResult();
        System.out.println("count of deleteBook"+count);
        if(count==0) {
            session.delete(book);
            session.getTransaction().commit();

            session.close();

            return true;
        }
        else{
            session.getTransaction().commit();

            session.close();

            return false;
        }

    }

    @Override
    public void updateBook(Book book) {

        Session session = sessionFactory.openSession();
        // begin a transaction
        session.beginTransaction();
        // update and commit transaction
        try {
            String retrieve = "select count(*) from TransactionBooks tb " +
                    " where tb.returnDate" +
                    " is null and tb.book.bookId=:bookId";
            Query retrieveQuery = (Query) session.createQuery(retrieve);
            retrieveQuery.setParameter("bookId", book.getBookId());
            //System.out.println((int)retrieveQuery.getResultList().size()+"in transaction query result");
            //System.out.println(retrieveQuery.getResultList() + "only today");

            Long outstandingBooks = (Long) retrieveQuery.getResultList().get(0);

            book.setNoOfAvailableCopies(book.getNoOfCopies()-toIntExact(outstandingBooks));
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
