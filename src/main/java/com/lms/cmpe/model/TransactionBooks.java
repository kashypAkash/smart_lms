package com.lms.cmpe.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Nischith on 11/26/2016.
 */

@Entity
public class TransactionBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionBooksId;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="transactionId")
    private Transaction transaction;

    private Date dueDate;
    private Date returnDate;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="bookId")
    private Book book;

    private int noOfTimesRenewed;
    private int fine;
}
