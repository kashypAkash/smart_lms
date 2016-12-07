package com.lms.cmpe.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nischith on 11/26/2016.
 */
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="userId")
    private User user;

    private Date transactionDate;

    @OneToMany(mappedBy = "transaction",cascade = CascadeType.ALL)
    private List<TransactionBooks> transactionBooksList = new ArrayList<TransactionBooks>();

    public List<TransactionBooks> getTransactionBooksList() {
        return transactionBooksList;
    }

    public void setTransactionBooksList(List<TransactionBooks> transactionBooksList) {
        this.transactionBooksList = transactionBooksList;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public  void addTransactionBooks(){

        List<TransactionBooks> transactionBooks=this.getTransactionBooksList();

        for (TransactionBooks transactionBook:transactionBooks) {
            transactionBook.setTransaction(this);
        }
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
