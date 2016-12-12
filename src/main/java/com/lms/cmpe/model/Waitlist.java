package com.lms.cmpe.model;

import javax.persistence.*;

/**
 * Created by Nischith on 11/26/2016.
 */
@Entity
public class Waitlist {
    public Waitlist() {
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int waitlistId;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="userId")
    private User user;

    public int getWaitlistId() {
        return waitlistId;
    }

    public void setWaitlistId(int waitlistId) {
        this.waitlistId = waitlistId;
    }


    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="bookId")
    private Book book;

    public Waitlist(User user, Book book) {
        this.user = user;
        this.book = book;
    }
}
