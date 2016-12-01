package com.lms.cmpe.controller;

import com.lms.cmpe.model.Book;
import com.lms.cmpe.model.BookKeywords;
import com.lms.cmpe.service.BookService;

import com.lms.cmpe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Nischith on 11/27/2016.
 */
@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/book/add")
    public String addBook(@ModelAttribute Book book){

        List<BookKeywords> dummyKeyWords=new ArrayList<BookKeywords>();
        dummyKeyWords.add(new BookKeywords("testkw"));
        dummyKeyWords.add(new BookKeywords("testkw2"));

        BookKeywords bk1=new BookKeywords("bk1");
        BookKeywords bk2=new BookKeywords("bk2");
        Book dummybook = new Book("Author", " title", 12345, "publisher", 2014, "Location", 5, "Available",dummyKeyWords);

        dummybook.addBookKeywords();
        bookService.addBook(dummybook);

        return "badrequest";
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

    @RequestMapping("/book/keyword/{keyword}")
    public String getBooksByKeyword(Model model, @PathVariable("keyword") String keyword){
        List<BookKeywords> bookKeywords;

        String dummyKeyword = "testkw";

        bookKeywords = bookService.getBooksByKeyword(dummyKeyword);

        for (BookKeywords bookKeyword:bookKeywords) {
            System.out.println(bookKeyword.getBook().getAuthor() + " " + bookKeyword.getBookKeywordId());
        }
       /*model.addAttribute("user",user);
        model.addAttribute("userid",user.getUserId());
        model.addAttribute("addressid",user.getAddress().getAddressId());*/
        return "book";
    }

    @RequestMapping(value = "/book/update",method = RequestMethod.POST)
    public String updateBook(@ModelAttribute Book book){

        book = bookService.getBookById(1);
        book.setAuthor("New Author");
        book.setTitle("New Title");
        bookService.updateBook(book);

        return String.format("redirect:/book/%d",book.getBookId());
    }

    @RequestMapping(value = "/book/delete",method = RequestMethod.POST)
    public String deleteBook(@ModelAttribute Book book){
        book = bookService.getBookById(2);
        bookService.deleteBook(book);

        return String.format("redirect:/book/%d",book.getBookId());
    }

    @RequestMapping(value="/books",method = RequestMethod.GET)
    public String getAllBooks(){
        System.out.println("All books");
        List<Book> books=bookService.getAllBooks();
        for (Book book:books) {
                System.out.println("printing each book"+book.getBookId());
        }
        return "badrequest";
    }
}
