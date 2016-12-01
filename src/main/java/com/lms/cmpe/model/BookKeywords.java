package com.lms.cmpe.model;

import javax.persistence.*;

/**
 * Created by Nischith on 11/26/2016.
 */

@Entity
public class BookKeywords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookKeywordId")
    private int bookKeywordId;
    private String keyword;

    //private int bookId;
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    public BookKeywords() {
    }

    public BookKeywords(int bookKeywordId, String keyword, Book book) {

        this.bookKeywordId = bookKeywordId;
        this.keyword = keyword;
        this.book = book;
    }

    public BookKeywords(String keyword, Book book) {

        this.keyword = keyword;
        this.book = book;
    }
    public BookKeywords(String keyword) {

        this.keyword = keyword;
    }

    public int getBookKeywordId() {
        return bookKeywordId;
    }

    public void setBookKeywordId(int bookKeywordId) {
        this.bookKeywordId = bookKeywordId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


}
