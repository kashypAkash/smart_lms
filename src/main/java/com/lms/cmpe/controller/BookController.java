package com.lms.cmpe.controller;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.User;
import com.lms.cmpe.model.BookKeywords;
import com.lms.cmpe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Nischith on 11/27/2016.
 */
@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String getAllBooks(Model model, HttpSession session){

        model.addAttribute("user",session.getAttribute("user"));
        model.addAttribute("books",bookService.getAllBooks());
        return "allbooks";
    }

    @GetMapping("/book")
    public String bookForm(Model model,HttpSession session){
        Book book = new Book();
        model.addAttribute("user",session.getAttribute("user"));
        model.addAttribute("book",book);
        return "book";
    }

    @RequestMapping(value = "/book/add", method = RequestMethod.POST)
    public String addBook(@ModelAttribute Book book, Model model, @RequestParam(value="action") String action, HttpSession session){
        model.addAttribute("user",session.getAttribute("user"));
        if(action.equals("addkeyword")){
            book.getBookKeywordsList().add(new BookKeywords());
            model.addAttribute("book",book);
            return "book";
        }

        User user = (User) session.getAttribute("user");
        System.out.println("User Iddddddddddddddd " + user.getUserId());

        if(action.equals("add")){
            System.out.println(book.toString());
            book.addBookKeywords();
            bookService.addBook(book);
            return "test";
        }
        return "test";
    }

    @RequestMapping("/book/{id}")
    public String getBookById(Model model, @PathVariable("id") int id){
        Book book = new Book();

        int dummyId = 1;

        book = bookService.getBookById(id);
       /*model.addAttribute("user",user);
        model.addAttribute("userid",user.getUserId());
        model.addAttribute("addressid",user.getAddress().getAddressId());*/
        return "book";
    }

    @GetMapping("/book/update/{id}")
    public String bookUpdateForm(@PathVariable("id") int id, Model model, HttpSession session){
        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("user", session.getAttribute("user"));
        return "updatebook";
    }

    @RequestMapping(value = "/book/update",method = RequestMethod.POST)
    public String updateBook(@ModelAttribute Book book, @PathVariable("id") int id,
                             @RequestParam(value="action", required=true) String action, HttpSession session,
                             Model model){
        model.addAttribute("user",session.getAttribute("user"));
        System.out.println(book.toString());
        book.setBookId(id);
        System.out.println(book.toString());
        if(action.equals("update")){
            bookService.updateBook(book);
        }

        /*if(action.equals("delete")){
            bookService.deleteUser(book);
        }*/
        return String.format("redirect:/book/%d",book.getBookId());
    }

    @GetMapping("/book/delete/{id}")
    public String deleteBookByid(@PathVariable("id") int id){
        Book book= bookService.getBookById(id);
        bookService.deleteBook(book);
        return "redirect:/books";
    }
}
