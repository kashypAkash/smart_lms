package com.lms.cmpe.controller;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.service.TransactionService;
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
    private TransactionService transactionService;

    @RequestMapping("/getCheckedOutBooks/{id}")
    public String getCheckedOutBooks(Model model, @PathVariable("id") int id){
        List<Book> books = new ArrayList<Book>();

        id = 1;

        books = transactionService.getCheckedOutBooksByUser(id);
       /*model.addAttribute("user",user);
        model.addAttribute("userid",user.getUserId());
        model.addAttribute("addressid",user.getAddress().getAddressId());*/
        return "book";
    }

}
