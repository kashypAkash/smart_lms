package com.lms.cmpe.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Nischith on 11/26/2016.
 */
@Entity
public class WaitlistBooksToBeAssigned {
    public int getWaitlistBooksToBeAssignedId() {
        return waitlistBooksToBeAssignedId;
    }

    public void setWaitlistBooksToBeAssignedId(int waitlistBooksToBeAssignedId) {
        this.waitlistBooksToBeAssignedId = waitlistBooksToBeAssignedId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public boolean isInvalid() {
        return isInvalid;
    }

    public void setInvalid(boolean invalid) {
        isInvalid = invalid;
    }

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int waitlistBooksToBeAssignedId;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="userId")
    private User user;

    public WaitlistBooksToBeAssigned() {
    }

    public WaitlistBooksToBeAssigned(User user, Book book, Date currentDate) {
        this.user = user;

        this.book = book;
        this.currentDate = currentDate;
    }

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="bookId")
    private Book book;

    private Date currentDate;
    private boolean isInvalid;
}
