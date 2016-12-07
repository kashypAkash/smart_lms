package com.lms.cmpe.service;

import com.lms.cmpe.dao.BookDao;
import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.BookKeywords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nischith on 11/27/2016.
 */
@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> getAllBooks() { return bookDao.getAllBooks(); }

    @Override
    public List<BookKeywords> getBooksByKeyword(String keyword)  { return bookDao.getBooksByKeyword(keyword); }

    @Override
    public Book getBookById(int id)  { return bookDao.getBookById(id); }

    @Override
    public void addBook(Book book) { bookDao.addBook(book);};

    @Override
    public void deleteBook(Book book) { bookDao.deleteBook(book);};

    @Override
    public void updateBook(Book book) { bookDao.updateBook(book);}

    @Override
    public List<Book> getBooksByKey(String key) {
        return bookDao.getBooksByKey(key);
    }

    ;


}
