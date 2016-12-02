package com.lms.cmpe.model;

import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 12/1/16.
 */
@ComponentScan
public class BookList {
    private List<Book> bookList = new ArrayList<Book>();

    public BookList() {
    }

    public BookList(List<Book> bookList) {

        this.bookList = bookList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
