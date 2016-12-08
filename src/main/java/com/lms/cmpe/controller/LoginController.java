package com.lms.cmpe.controller;


import com.lms.cmpe.model.TransactionBooks;
import com.lms.cmpe.model.User;
import com.lms.cmpe.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 11/26/16.
 */

@Controller
public class LoginController {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private BookService bookService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionBooksService transactionBooksService;

    @GetMapping("/")
    public String loginForm(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "login";
    }

    @PostMapping("/login")
    public String validateLogin(@ModelAttribute User user, Model model, HttpSession session){

        User result = userService.getUserByEmail(user.getEmail());
        if(result!=null && result.getPassword().equals(user.getPassword())){
            user = result;
            session.setAttribute("user",user);
            model.addAttribute("user",user);
                if(result.isVerified()) {

                    return "redirect:/profile";
                }
                else{
                    return "activate";
                }
        }
        System.out.println("Reached here");
        model.addAttribute("error","Wrong Username or Password");
        return "login";
    }


    @GetMapping("/signup")
    public String signUpForm(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "signup";
    }

    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user,@RequestParam(value="userRole", required=false) String role){
        user.setVerificationCode(Integer.toString(verificationService.verficationCode()));
        if(role != null){
            user.setUserRole(role);
        }

        if(userService.saveUser(user)) {
            mailService.sendMail(user);
            return "activate";
        }
        return "test";
    }

    @PostMapping("/activate")
    public String activateAccount(@RequestParam(value="activationcode", required=true) String activationcode,
                                  @RequestParam(value="userid", required=true) String userid,
                                  Model model){
        System.out.println(userid);
        User user = userService.getUserById(Integer.parseInt(userid));
        model.addAttribute("user",user);
        if(activationcode.equals(user.getVerificationCode())){
            user.setVerified(true);
            userService.updateUser(user);
            return "redirect:/profile";
        }

        model.addAttribute("message","Incorrect Verification Code! Try again");
        return "activate";
    }

    @GetMapping("/profile")
    public String profile(Model model,HttpSession session){

        if(session.getAttribute("user")!=null){

            model.addAttribute("user",session.getAttribute("user"));
            User tempuser = (User)session.getAttribute("user");
            // TODO: Akash replace -> mybooks from transaction table
            model.addAttribute("mybooks",transactionService.getBooksToBeReturned(tempuser.getUserId()));

            if(session.getAttribute("returnlist")==null){
                List<TransactionBooks> returnlist = new ArrayList<TransactionBooks>();
                session.setAttribute("returnlist",returnlist);
            }

            model.addAttribute("returnlist", session.getAttribute("returnlist"));

            return "profile";
        }
        return "redirect:/";
    }

    @GetMapping("/return/book/{id}")
    public String returnCart(@PathVariable("id") int id,
                                Model model, HttpSession session){

        List<TransactionBooks> returnlist = (ArrayList<TransactionBooks>)session.getAttribute("returnlist");
        for(TransactionBooks transactionBook:returnlist){
            if(transactionBook.getTransactionBooksId() == id){
                return "redirect:/profile";
            }
        }
        returnlist.add(transactionBooksService.getTransactionBookById(id));
        return "redirect:/profile";
    }

    @GetMapping("/cancelreturn/book/{id}/{index}")
    public String returnToLibrary(@PathVariable("id") int id
            ,@PathVariable("index") int index, HttpSession session){

        List<TransactionBooks> returnlist = (ArrayList<TransactionBooks>)session.getAttribute("returnlist");

        returnlist.remove(index);
        return "redirect:/profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
