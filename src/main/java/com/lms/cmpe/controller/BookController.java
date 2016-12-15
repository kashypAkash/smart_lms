package com.lms.cmpe.controller;

import com.lms.cmpe.model.*;
import com.lms.cmpe.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nischith on 11/27/2016.
 */
@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MailService mailService;

    @Autowired
    private WaitlistService waitlistService;

    @Autowired
    private WaitlistBooksToBeAssignedService waitlistBooksToBeAssignedService;

    @GetMapping("/books")
    public String getAllBooks(Model model, HttpSession session){

        if(session.getAttribute("user")==null){
            return "redirect:/";
        }

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

    @GetMapping("/book/addtowaitlist/{id}")
    public String addToWaitlist(Model model, HttpSession session,@PathVariable("id") int id)
    {
        Book book = bookService.getBookById(id);
        User user = (User) session.getAttribute("user");
        Waitlist waitlist = new Waitlist();
        waitlist.setUser(user);
        waitlist.setBook(book);
        waitlistService.storeWaitlist(waitlist);
        return "redirect:/waitListedbook";
    }
    @GetMapping("/waitListedbook")
    public String getWaitlistedBooks(Model model,HttpSession session)
    {
        model.addAttribute("user",session.getAttribute("user"));
        User user = (User)session.getAttribute("user");
        List<Book> waitlistBookstobeaddedbookList = waitlistBooksToBeAssignedService.getWaitlistedBooks(user);
        List<Book> waitlistbooklist = waitlistService.getWaitlistBooks(user);
        model.addAttribute("waitlistbooklist",waitlistbooklist);
        model.addAttribute("bookList",waitlistBookstobeaddedbookList);
        return "waitlist";
    }

    @GetMapping("/book")
    public String bookForm(Model model, HttpSession session){

        if(session.getAttribute("user")==null){
            return "redirect:/";
        }

        Book book = new Book();
        model.addAttribute("user",session.getAttribute("user"));
        model.addAttribute("book",book);

        return "book";
    }

    @RequestMapping(value = "/book/add", method = RequestMethod.POST)
    public String addBook(@ModelAttribute Book book, Model model, @RequestParam(value="action") String action,
                          HttpSession session, RedirectAttributes redirectAttributes){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }

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
            redirectAttributes.addFlashAttribute("message","The Book " + book.getTitle() +"is successfully added! Add more books..");
            return"redirect:/book";
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
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }

        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("user", session.getAttribute("user"));
        return "updatebook";
    }

    @RequestMapping(value = "/book/update/{id}",method = RequestMethod.POST)
    public String updateBook(@ModelAttribute Book book, @PathVariable("id") int id,
                             @RequestParam(value="action", required=true) String action, HttpSession session,
                             Model model, RedirectAttributes redirectAttributes){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }

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
            redirectAttributes.addFlashAttribute("message","Book info is updated");
        }

        return String.format("redirect:/book/update/%d",book.getBookId());
    }

    @GetMapping("/book/delete/{id}")
    public String deleteBookByid(@PathVariable("id") int id, HttpSession session, RedirectAttributes redirectAttributes){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }

        Book book= bookService.getBookById(id);
        bookService.deleteBook(book);
        redirectAttributes.addFlashAttribute("message", book.getTitle() +" has been deleted");
        return "redirect:/books";
    }

    @GetMapping("/book/addtocart/{id}")
    public String addBookToCart(@PathVariable("id") int id, @ModelAttribute BookList booklist,
                                Model model, HttpSession session){
            if(session.getAttribute("user")==null){
                return "redirect:/";
            }

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

        if(session.getAttribute("user")==null){
            return "redirect:/";
        }

        booklist = (BookList)session.getAttribute("booklist");
        booklist.getBookList().remove(index);
        return "redirect:/books";
    }

    @GetMapping("/books/checkout")
    public String checkout(HttpSession session, Model model, RedirectAttributes redirectAttributes){

        if(session.getAttribute("user")==null){
            return "redirect:/";
        }

        BookList bookList= (BookList)session.getAttribute("booklist");
        System.out.println(bookList.toString());
        Transaction t=new Transaction();

        Date dateobj = (Date)session.getAttribute("appTime");

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
        User u=(User)session.getAttribute("user");
        Transaction transaction = transactionService.checkOutBooks(t,u.getUserId(),(Date)session.getAttribute("appTime"));
        if(transaction == null){
            redirectAttributes.addFlashAttribute("message","Your Daily/Total limit of checkout books has been reached");
            return "redirect:/myerror";
        }
        model.addAttribute("transaction",transaction);
        model.addAttribute("user",session.getAttribute("user"));
        mailService.sendTransactionInfoMail(transaction,(User)session.getAttribute("user"));
        session.removeAttribute("booklist");
        return "checkout";
    }


    @GetMapping("/books/searchresults")
    public String searchResults(Model model, HttpSession session){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }

        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("booklist", session.getAttribute("booklist"));
        model.addAttribute("books", session.getAttribute("books"));
        return "searchresults";
    }

    @GetMapping("/return/books/checkout")
    public String processBookReturns(HttpSession session, Model model){
        ArrayList<TransactionBooks> transactionBooksList = (ArrayList<TransactionBooks>)session.getAttribute("returnlist");

        User user = (User)session.getAttribute("user");
        Date appTime=(Date)session.getAttribute("appTime");

        boolean successfullyReturned = transactionService.returnBooks(transactionBooksList,user.getUserId(),appTime);

        session.removeAttribute("returnlist");
        return "redirect:/profile";
    }

    @PostMapping(value = "/books", params = "search")
    public String search(Model model, HttpSession session, @RequestParam("keyword") String keyword){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }

        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("books", bookService.getBooksByKey(keyword));
        session.setAttribute("books",bookService.getBooksByKey(keyword));
        model.addAttribute("booklist", session.getAttribute("booklist"));
        return "redirect:/books/searchresults";
    }


    @GetMapping("/book/delete/searchresult/{id}")
    public String deleteBookSearchedByid(@PathVariable("id") int id, HttpSession session){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }

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
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }

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

        if(session.getAttribute("user")==null){
            return "redirect:/";
        }

        booklist = (BookList)session.getAttribute("booklist");
        booklist.getBookList().remove(index);
        return "redirect:/books/searchresults";
    }

    @GetMapping("reissue/book/{id}")
    public String reIssueBook(@PathVariable("id") int id, HttpSession session)
    {
        System.out.println("Reissue Hereeeeeeeeeeeeeeeee " + id);
        User user = (User)session.getAttribute("user");
        transactionService.reissueBook(id, user.getUserId());
        return "redirect:/profile";

    }

    @GetMapping("/myerror")
    public String customError(Model model, HttpSession session){
        model.addAttribute("user",session.getAttribute("user"));
        return "test";
    }



}
