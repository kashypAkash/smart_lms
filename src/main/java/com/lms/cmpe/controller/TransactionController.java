package com.lms.cmpe.controller;


import com.lms.cmpe.model.Book;
import com.lms.cmpe.service.BookService;
import com.lms.cmpe.service.TransactionService;
import com.lms.cmpe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Nischith on 11/29/2016.
 */
@Controller
public class TransactionController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/getCheckedOutBooks/{id}")
    public String getCheckedOutBooksByUser(Model model, @PathVariable("id") int id){
        List<Book> books = new ArrayList<Book>();

        id = 1;

        books = transactionService.getCheckedOutBooksByUser(id);
       /*model.addAttribute("user",user);
        model.addAttribute("userid",user.getUserId());
        model.addAttribute("addressid",user.getAddress().getAddressId());*/
        return "book";
    }
/*
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkOutBooks(Model model, HttpSession session){
        boolean checkOutSuccessful = false;
        int id = 1;

        Transaction transaction = new Transaction();
        transaction.setTransactionDate(new Date());

        //user.getUserId()
        transaction.setUser(userService.getUserById(1));
        ArrayList<TransactionBooks> tbs=new ArrayList<TransactionBooks>();
        Book b=bookService.getBookById(1);
        TransactionBooks tb=new TransactionBooks();
        tb.setBook(b);
        tb.setDueDate(new Date(12022016));
        //tb.setReturnDate(null);

        tbs.add(tb);
        transaction.setTransactionBooksList(tbs);


        checkOutSuccessful = transactionService.checkOutBooks(transaction, id);
       *//*model.addAttribute("user",user);
        model.addAttribute("userid",user.getUserId());
        model.addAttribute("addressid",user.getAddress().getAddressId());*//*
        return "book";
    }*/
}
