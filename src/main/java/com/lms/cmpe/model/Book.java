package com.lms.cmpe.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Nischith on 11/26/2016.
 */

@Entity
public class Book {

    public Collection<BookKeywords> getBookKeywords() {
        return bookKeywords;
    }

    public void setBookKeywords(Collection<BookKeywords> bookKeywords) {
        this.bookKeywords = bookKeywords;
    }

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    private String author;
    private int callNumber;
    private String publisher;
    private int yearOfPublication;
    private String locationInLibrary;
    private int noOfCopies;
    private String currentStatus;

    @OneToMany(mappedBy = "bookId")
    private Collection<BookKeywords> bookKeywords;

    public Book(){}

    public Book(String author, int callNumber, String publisher, int yearOfPublication, String locationInLibrary, int noOfCopies, String currentStatus) {
        this.author = author;
        this.callNumber = callNumber;
        this.publisher = publisher;
        this.yearOfPublication = yearOfPublication;
        this.locationInLibrary = locationInLibrary;
        this.noOfCopies = noOfCopies;
        this.currentStatus = currentStatus;
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

    public int getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(int noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}
