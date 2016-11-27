package com.lms.cmpe.model;

import javax.persistence.*;

/**
 * Created by Nischith on 11/26/2016.
 */

@Entity
public class BookKeywords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookKeywordId;
    private String keyword;

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




    private int bookId;
}
