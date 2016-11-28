package com.lms.cmpe.service;

import com.lms.cmpe.model.Book;

import java.util.List;

/**
 * Created by Nischith on 11/27/2016.
 */

public interface BookService {
    List<Book> getAllBooks();
    List<Book> getBooksByKeyword(String keyword);
    Book getBookById(int id);
    void addBook(Book book);
    void deleteBook(Book book);
    void updateBook(Book book);
}
