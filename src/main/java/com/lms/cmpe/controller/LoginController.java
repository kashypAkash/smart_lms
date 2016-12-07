package com.lms.cmpe.controller;


import com.lms.cmpe.model.User;
import com.lms.cmpe.service.MailService;
import com.lms.cmpe.service.UserService;
import com.lms.cmpe.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

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

        userService.saveUser(user);
        mailService.sendMail(user);
        return "activate";
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
            return "profile";
        }

        model.addAttribute("message","Incorrect Verification Code! Try again");
        return "activate";
    }

    @GetMapping("/profile")
    public String profile(Model model,HttpSession session){
        if(session.getAttribute("user")!=null){
            model.addAttribute("user",session.getAttribute("user"));
            return "profile";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
