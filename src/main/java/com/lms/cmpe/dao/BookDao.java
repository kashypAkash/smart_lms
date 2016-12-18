package com.lms.cmpe.dao;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.BookKeywords;

import java.util.List;

/**
 * Created by Nischith on 11/27/2016.
 */
public interface BookDao {

    void addBook(Book book);
    void updateBook(Book book);
    boolean deleteBook(Book book);

    List<BookKeywords> getBooksByKeyword(String keyword);
    List<Book> getAllBooks();
    Book getBookById(int id);
    List<Book> getBooksByKey(String key);
}
