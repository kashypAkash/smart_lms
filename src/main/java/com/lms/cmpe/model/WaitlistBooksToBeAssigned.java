package com.lms.cmpe.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Nischith on 11/26/2016.
 */
@Entity
public class WaitlistBooksToBeAssigned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int waitlistBooksToBeAssignedId;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="bookId")
    private Book book;

    private Date currentDate;
    private boolean isInvalid;
}
