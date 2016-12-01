package com.lms.cmpe.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by Nischith on 11/26/2016.
 */

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookId")
    private int bookId;
    private String author;
    private String title;
    private int callNumber;
    private String publisher;
    private int yearOfPublication;
    private String locationInLibrary;
    private int noOfCopies;
    private int noOfAvailableCopies;
    private String currentStatus;

   @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    private List<BookKeywords> bookKeywordsList;

    public Book(){}

    public Book( String author, String title, int callNumber, String publisher, int yearOfPublication, String locationInLibrary, int noOfCopies, String currentStatus,List<BookKeywords> bookKeywordsList) {
        this.author = author;
        // this.bookId = bookId;
        this.author = author;
        this.title = title;
        this.callNumber = callNumber;
        this.publisher = publisher;
        this.yearOfPublication = yearOfPublication;
        this.locationInLibrary = locationInLibrary;
        this.noOfCopies = noOfCopies;
        this.currentStatus = currentStatus;
        this.bookKeywordsList = bookKeywordsList;
    }

    public Book(String author, String title, int callNumber, String publisher, int yearOfPublication, String locationInLibrary, int noOfCopies, int noOfAvailableCopies, String currentStatus, List<BookKeywords> bookKeywordsList) {
        this.author = author;
        this.title = title;
        this.callNumber = callNumber;
        this.publisher = publisher;
        this.yearOfPublication = yearOfPublication;
        this.locationInLibrary = locationInLibrary;
        this.noOfCopies = noOfCopies;
        this.noOfAvailableCopies = noOfAvailableCopies;
        this.currentStatus = currentStatus;
        this.bookKeywordsList = bookKeywordsList;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public int getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(int callNumber) {
        this.callNumber = callNumber;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getLocationInLibrary() {
        return locationInLibrary;
    }

    public void setLocationInLibrary(String locationInLibrary) {
        this.locationInLibrary = locationInLibrary;
    }

    public int getNoOfCopies() { return noOfCopies; }

    public void setNoOfCopies(int noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public int getNoOfAvailableCopies() { return noOfAvailableCopies; }

    public void setNoOfAvailableCopies(int noOfAvailableCopies) { this.noOfAvailableCopies = noOfAvailableCopies; }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<BookKeywords> getBookKeywords() {
        return bookKeywordsList;
    }

    public List<BookKeywords> getBookKeywordsList() {
        return bookKeywordsList;
    }

    public void setBookKeywordsList(List<BookKeywords> bookKeywordsList) {
        this.bookKeywordsList = bookKeywordsList;
    }

    public void setBookKeywords(List<BookKeywords> bookKeywordsList) {
        this.bookKeywordsList = bookKeywordsList;
    }


    public  void addBookKeywords(){

        List<BookKeywords> bookKeywords=this.getBookKeywordsList();

        for (BookKeywords bookKeyword:bookKeywords) {
            bookKeyword.setBook(this);
        }
    }
}
