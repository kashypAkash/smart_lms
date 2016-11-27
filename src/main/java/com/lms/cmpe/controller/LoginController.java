package com.lms.cmpe.controller;

import com.lms.cmpe.model.User;
import com.lms.cmpe.service.MailService;
import com.lms.cmpe.service.UserService;
import com.lms.cmpe.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/login")
    public String signUp(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "login";
    }

    @PostMapping("/login")
    public String createUser(@ModelAttribute User user){
        user.setVerificationCode(Integer.toString(verificationService.verficattionCode()));
        System.out.println(user.toString());
        userService.saveUser(user);
        mailService.sendMail(user);
        return null;
    }

}
