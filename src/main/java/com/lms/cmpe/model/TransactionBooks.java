package com.lms.cmpe.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Nischith on 11/26/2016.
 */

@Entity
public class TransactionBooks {
    public TransactionBooks(int transactionBooksId, Transaction transaction, Date dueDate, Date returnDate, Book book, int noOfTimesRenewed, int fine) {
        this.transactionBooksId = transactionBooksId;
        this.transaction = transaction;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.book = book;
        this.noOfTimesRenewed = noOfTimesRenewed;
        this.fine = fine;
    }

    public TransactionBooks(Transaction transaction, Date dueDate, Date returnDate, Book book, int noOfTimesRenewed, int fine) {
        this.transaction = transaction;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.book = book;
        this.noOfTimesRenewed = noOfTimesRenewed;
        this.fine = fine;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionBooksId;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="transactionId")
    private Transaction transaction;

    public int getTransactionBooksId() {
        return transactionBooksId;
    }

    public void setTransactionBooksId(int transactionBooksId) {
        this.transactionBooksId = transactionBooksId;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public TransactionBooks() {
    }

    public int getNoOfTimesRenewed() {

        return noOfTimesRenewed;
    }

    public void setNoOfTimesRenewed(int noOfTimesRenewed) {
        this.noOfTimesRenewed = noOfTimesRenewed;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    private Date dueDate;
    private Date returnDate;
    @ManyToOne
    @JoinColumn(name="bookId")
    private Book book;

    private int noOfTimesRenewed;
    private int fine;
}
