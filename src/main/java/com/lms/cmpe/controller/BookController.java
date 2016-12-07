package com.lms.cmpe.controller;

import com.lms.cmpe.model.*;
import com.lms.cmpe.service.BookService;
import com.lms.cmpe.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Nischith on 11/27/2016.
 */
@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/books")
    public String getAllBooks(Model model, HttpSession session){
            model.addAttribute("user",session.getAttribute("user"));
            model.addAttribute("books",bookService.getAllBooks());

            if(session.getAttribute("books")!=null){
                session.removeAttribute("books");
            }

            if(session.getAttribute("booklist")==null){
                BookList bookList = new BookList();
                session.setAttribute("booklist",bookList);
            }

            model.addAttribute("booklist", session.getAttribute("booklist"));
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

        if(action.equals("add")){
            System.out.println(book.toString());
            book.addBookKeywords();
            int total = book.getNoOfAvailableCopies();
            book.setNoOfAvailableCopies(total);
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

    @RequestMapping(value = "/book/update/{id}",method = RequestMethod.POST)
    public String updateBook(@ModelAttribute Book book, @PathVariable("id") int id,
                             @RequestParam(value="action", required=true) String action, HttpSession session,
                             Model model){
        model.addAttribute("user",session.getAttribute("user"));
        book.setBookId(id);

        if(action.equals("addkeyword")){
            book.getBookKeywordsList().add(new BookKeywords());
            model.addAttribute("book",book);
            return "updatebook";
        }

        System.out.println(book.toString());
        if(action.equals("update")){
            book.addBookKeywords();
            bookService.updateBook(book);
        }

        return String.format("redirect:/book/update/%d",book.getBookId());
    }

    @GetMapping("/book/delete/{id}")
    public String deleteBookByid(@PathVariable("id") int id){
        Book book= bookService.getBookById(id);
        bookService.deleteBook(book);
        return "redirect:/books";
    }

    @GetMapping("/book/addtocart/{id}")
    public String addBookToCart(@PathVariable("id") int id, @ModelAttribute BookList booklist,
                                Model model, HttpSession session){
            booklist = (BookList)session.getAttribute("booklist");

            for(Book book:booklist.getBookList()){
                if(book.getBookId() == id){
                    return "redirect:/books";
                }
            }
            booklist.getBookList().add(bookService.getBookById(id));
        return "redirect:/books";
    }


    @GetMapping("/book/remove/{id}/{index}")
    public String removeFromCart(@PathVariable("id") int id, @ModelAttribute BookList booklist
                                ,@PathVariable("index") int index, HttpSession session){

        booklist = (BookList)session.getAttribute("booklist");
        booklist.getBookList().remove(index);
        return "redirect:/books";
    }

    @GetMapping("/books/checkout")
    public String checkout(HttpSession session, Model model){

        BookList bookList= (BookList)session.getAttribute("booklist");
        System.out.println(bookList.toString());
        Transaction t=new Transaction();

        Date dateobj = new Date();

        t.setTransactionDate(dateobj);
        Calendar c = Calendar.getInstance();
        c.setTime(dateobj);
        c.add(Calendar.DATE, 30);


        t.setUser((User)session.getAttribute("user"));
        ArrayList<TransactionBooks> tbs=new ArrayList<TransactionBooks>();
        for (Book book:bookList.getBookList()) {
            TransactionBooks tb=new TransactionBooks();
            tb.setBook(book);
            tb.setDueDate(c.getTime());
            tb.setTransaction(t);
            tbs.add(tb);
        }
        t.setTransactionBooksList(tbs);
        System.out.println(t.getUser()+t.getTransactionBooksList().get(0).getBook().getAuthor()+"in check out books");
        //TransactionService ts=new TransactionServiceImpl();
        //System.out.println("checking for null error");
        //System.out.println(ts);
        Transaction transaction = transactionService.checkOutBooks(t,1);
        if(transaction == null){
            return "test"; // TODO: bad request ; return a error page

        }
        model.addAttribute("transaction",transaction);
        model.addAttribute("user",session.getAttribute("user"));
        session.removeAttribute("booklist");
        return "checkout";
    }

/*    @GetMapping("/books/booksToBeReturned")
    public String booksToBeReturned(HttpSession session){
        System.out.println(session.getAttribute("user"));
        transactionService.getBooksToBeReturned((int)((User)session.getAttribute("user")).getUserId());
        return "redirect:/books";
    }*/

    @GetMapping("/books/searchresults")
    public String searchResults(Model model, HttpSession session){
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("booklist", session.getAttribute("booklist"));
        model.addAttribute("books", session.getAttribute("books"));
        return "searchresults";
    }

    @GetMapping("/return/books/checkout")
    public String processBookReturns(HttpSession session, Model model){
        System.out.println(session.getAttribute("returnlist"));
        //TODO: Nishchith process returns and remove from session and redirect to profile
        return "redirect:/profile";
    }

    @PostMapping(value = "/books", params = "search")
    public String search(Model model, HttpSession session, @RequestParam("keyword") String keyword){
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("books", bookService.getBooksByKey(keyword));
        session.setAttribute("books",bookService.getBooksByKey(keyword));
        model.addAttribute("booklist", session.getAttribute("booklist"));
        return "redirect:/books/searchresults";
    }


    @GetMapping("/book/delete/searchresult/{id}")
    public String deleteBookSearchedByid(@PathVariable("id") int id, HttpSession session){
        Book book= bookService.getBookById(id);
        bookService.deleteBook(book);
        ArrayList<Book> templist = (ArrayList<Book>)session.getAttribute("books");
        for(Book item: templist){
            if(item.getBookId() == id){
                ((ArrayList<Book>) session.getAttribute("books")).remove(item);
                return "redirect:/books/searchresults";
            }
        }

        return "redirect:/books/searchresults";
    }

    @GetMapping("/book/addtocart/searchresult/{id}")
    public String addBookSearchedToCart(@PathVariable("id") int id, @ModelAttribute BookList booklist,
                                Model model, HttpSession session){
        booklist = (BookList)session.getAttribute("booklist");

        for(Book book:booklist.getBookList()){
            if(book.getBookId() == id){
                return "redirect:/books/searchresults";
            }
        }
        booklist.getBookList().add(bookService.getBookById(id));
        return "redirect:/books/searchresults";
    }

    @GetMapping("/book/remove/searchresult/{id}/{index}")
    public String removeSearchResultFromCart(@PathVariable("id") int id, @ModelAttribute BookList booklist
            ,@PathVariable("index") int index, HttpSession session){

        booklist = (BookList)session.getAttribute("booklist");
        booklist.getBookList().remove(index);
        return "redirect:/books/searchresults";
    }





}
