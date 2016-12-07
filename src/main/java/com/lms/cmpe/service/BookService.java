package com.lms.cmpe.service;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.BookKeywords;

import java.util.List;

/**
 * Created by Nischith on 11/27/2016.
 */

public interface BookService {
    List<Book> getAllBooks();
    List<BookKeywords> getBooksByKeyword(String keyword);
    Book getBookById(int id);
    void addBook(Book book);
    void deleteBook(Book book);
    void updateBook(Book book);
    List<Book> getBooksByKey(String key);
}
