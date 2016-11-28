package com.lms.cmpe.controller;


import com.lms.cmpe.model.Address;
import com.lms.cmpe.model.DataObj;
import com.lms.cmpe.model.Phone;
import com.lms.cmpe.model.User;
import com.lms.cmpe.service.PhoneService;
import com.lms.cmpe.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @Autowired
    private  SessionFactory sessionFactory;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/mail")
    public String test(){

            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setTo("senjusforest@gmail.com");
            mailMessage.setSubject("Registration");
            mailMessage.setText("Hello ");
            javaMailSender.send(mailMessage);
        return "home";

    }

    @PostMapping("/add")
    public String check(@RequestParam(value = "firstname") String firstname,
                      @RequestParam(value = "lastname") String lastname,
                      @RequestParam(value = "title") String title,
                      @RequestParam(value = "street") String street,
                      @RequestParam(value = "city") String city,
                      @RequestParam(value = "state") String state,
                      @RequestParam(value = "zip") String zip,
                      @RequestParam(value = "phones") String phones
                      ){

        User user = new User();
       /* List<Phone> phoneList = userService.getUsersByNumber(phones);
        if(phoneList!=null){
            User user = new User(firstname,lastname,title,new Address(street,city,state,zip),phoneList);
            userService.saveUser(user);
            return "redirect:/users";
        }*/
            return "badrequest";

    }

    @RequestMapping("/user/{id}")
    public String getUserById(Model model,@PathVariable("id") int id){
        User user = new User();
        /*User user = userService.getUserById(id);
        model.addAttribute("user",user);
        model.addAttribute("userid",user.getUserId());
        model.addAttribute("addressid",user.getAddress().getAddressId());*/
        return "user";
    }

    @RequestMapping(value = "/user/{id}", params = "json")
    @ResponseBody
    public User getJosn(Model model,@PathVariable("id") int id){
        User user = userService.getUserById(id);
        return user;
    }



    @RequestMapping(value = "/user/modify",method = RequestMethod.POST)
    public String modify(@ModelAttribute User user, @RequestParam(value="action", required=true) String action,
                                                    @RequestParam(value="id", required=true) int id,
                                                    @RequestParam(value="addressid", required=true) int addressId){

       /* user.setUserId(id);
        user.getAddress().setAddressId(addressId);

        if(action.equals("update")){

            userService.updateUser(user);
        }

        if(action.equals("delete")){
            userService.deleteUser(user);
        }*/
        return String.format("redirect:/user/%d",user.getUserId());
    }


    @RequestMapping(value = "/phone", method = RequestMethod.GET)
    public String phoneForm(Model model){
        Phone phone = new Phone();
        model.addAttribute("phone",phone);
        return "phone";
    }

    @RequestMapping(value = "/phone", method = RequestMethod.POST)
    public String createPhone(@ModelAttribute Phone phone){
        phoneService.createPhone(phone);
        return "phone";
    }

    @RequestMapping(value = "/phone/{id}", method = RequestMethod.GET)
    public String getPhone(Model model,@PathVariable("id") int id){

        model.addAttribute("phone",phoneService.getPhoneById(id));
        Session session = sessionFactory.openSession();
        List<User> userList = new ArrayList<>();

        try {
            Query query = session.createNativeQuery("SELECT user_id from user_phone where phone_id=:phone_id");
            query.setParameter("phone_id",id);
            List<Integer> userIds =  query.getResultList();

            for(Integer userid:userIds){
                userList.add(userService.getUserById(userid.intValue()));
            }
        }
        catch (NoResultException e){
            System.out.println(e.getMessage());
        }
        model.addAttribute("users",userList);
        session.close();

        return "editphone";
    }

    @RequestMapping(value = "/phone/{id}", params = "json")
    @ResponseBody
    public DataObj getJsonPhone(Model model, @PathVariable("id") int id){
        DataObj dataObj = new DataObj();
        dataObj.setPhone(phoneService.getPhoneById(id));

        Session session = sessionFactory.openSession();
        List<User> userList = new ArrayList<>();

        try {
            Query query = session.createNativeQuery("SELECT user_id from user_phone where phone_id=:phone_id");
            query.setParameter("phone_id",id);
            List<Integer> userIds =  query.getResultList();

            for(Integer userid:userIds){
                userList.add(userService.getUserById(userid.intValue()));
            }
        }
        catch (NoResultException e){
            System.out.println(e.getMessage());
        }
        dataObj.setUser(userList);
        session.close();
        return dataObj;
    }

    @RequestMapping(value = "/phone/{id}", method = RequestMethod.POST)
    public String updatePhone(@ModelAttribute Phone phone,@PathVariable("id") int id,
                              @RequestParam(value="action", required=true) String action,
                              @RequestParam(value="addressid", required=true) int addressId){
        phone.setPhoneId(id);
        phone.getAddress().setAddressId(addressId);

        if(action.equals("update")){
            phoneService.updatePhone(phone);
            return String.format("redirect:/phone/%d",phone.getPhoneId());
        }

        if(action.equals("delete")){
            phoneService.deletePhone(phone);
        }
        return "phone";
    }


    @RequestMapping("/users")
    public String users(Model modelMap){
        modelMap.addAttribute("users", userService.getUsers());
        return "home";
    }

}
