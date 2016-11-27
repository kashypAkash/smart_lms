package com.lms.cmpe.model;

import javax.persistence.*;
import java.util.Date;

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

}
