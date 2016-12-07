package com.lms.cmpe.service;

import com.lms.cmpe.model.Transaction;
import com.lms.cmpe.model.TransactionBooks;
import com.lms.cmpe.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
}
