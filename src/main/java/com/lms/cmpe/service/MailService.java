package com.lms.cmpe.service;

import com.lms.cmpe.model.Transaction;
import com.lms.cmpe.model.TransactionBooks;
import com.lms.cmpe.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by akash on 11/27/16.
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(User user){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Activate Your Account");
        mailMessage.setText("Activation Code:" + user.getVerificationCode() );
        javaMailSender.send(mailMessage);
    }
    public void sendTransactionInfoMail(Transaction transaction,User user){
        StringBuilder sb=new StringBuilder("TranactionId - ");
        System.out.println(transaction.getTransactionId());
        sb.append(transaction.getTransactionId());
        sb.append(System.getProperty("line.separator"));
        for (TransactionBooks transactionBook:transaction.getTransactionBooksList()) {
            System.out.println(transactionBook.getBook().getTitle());
            System.out.println(transactionBook.getDueDate());
            sb.append("Book Title - "+transactionBook.getBook().getTitle()+"  ");
            sb.append("Due Date - "+transactionBook.getDueDate());
            sb.append(System.getProperty("line.separator"));
        }
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Transaction Info");
        mailMessage.setText("Transaction Info:" + sb );
        javaMailSender.send(mailMessage);
    }

    public void sendTransactionReturnsInfoMail(ArrayList<TransactionBooks> transactionBooks,ArrayList fineList, User user, Date returnDate){
        StringBuilder sb=new StringBuilder("The following books have been returned");
        sb.append(System.getProperty("line.separator"));

        int counter=0;
        for (TransactionBooks transactionBook:transactionBooks) {
            sb.append("Book Title - "+transactionBook.getBook().getTitle()+"  ");
            sb.append(System.getProperty("line.separator"));
            sb.append("Return Date - "+returnDate);
            sb.append(System.getProperty("line.separator"));
            if((long)fineList.get(counter)>0){
                sb.append("fine - "+fineList.get(counter));
            }
            counter++;
            sb.append(System.getProperty("line.separator"));
        }
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Books Returned information");
        mailMessage.setText("Books Returned information:" + sb );
        javaMailSender.send(mailMessage);
    }

    public void sendSuccessfulRegistrationMail(User user){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Activation successful");
        mailMessage.setText("Your account has been successfully activated");
        javaMailSender.send(mailMessage);
    }
    public void sendReminderMail(User user,TransactionBooks transactionBook){
        StringBuilder sb=new StringBuilder("You have not returned the following book and due date is near or you passed due date. Please return it");
        sb.append(System.getProperty("line.separator"));
        sb.append("Book Title - "+transactionBook.getBook().getTitle());
        sb.append(System.getProperty("line.separator"));
        sb.append("Due Date - "+transactionBook.getDueDate());
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Reminder Mail --- Book Title ---"+transactionBook.getBook().getTitle());
        mailMessage.setText("Reminder Mail"+sb);
        javaMailSender.send(mailMessage);
    }
}
