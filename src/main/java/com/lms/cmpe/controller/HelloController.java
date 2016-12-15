package com.lms.cmpe.controller;


import com.lms.cmpe.model.ApplicationTime;
import com.lms.cmpe.model.User;
import com.lms.cmpe.service.UserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Controller
public class HelloController {

    @Autowired
    private  SessionFactory sessionFactory;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ApplicationTime applicationTime;


    @RequestMapping(value = "/getDate",method = RequestMethod.POST)
    public String getDate(@RequestParam("dateValue") String dateInString, RedirectAttributes redirectAttributes,HttpSession session)
    {


        if(applicationTime.setAppDateTime(dateInString)){
            redirectAttributes.addFlashAttribute("message",dateInString);
            Date appTime;
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
            try {
                appTime = formatter.parse(dateInString);
                session.setAttribute("appTime", appTime);
            }catch (Exception e){
                e.printStackTrace();
            }
            return "redirect:/profile";
        }
        else{
            redirectAttributes.addFlashAttribute("message","invalid date format");
            return "redirect:/myerror";
        }
        //ApplicationTime app = new ApplicationTime(appDateTime);


        //return "redirect:myerror"
    }

    public String parseDate(String input, Map<String,String> map)
    {
        String finalText = "";
        String text = input.substring(input.indexOf(' ')+1);
        text = text.substring(0,text.indexOf(' '));
        String[] array = input.split("\\s+");
        for(String key : map.keySet())
        {
            if(key.equals(text))
            {
                text = map.get(key);
                break;
            }
        }
        finalText = array[2]+"/"+text+"/"+array[3]+" "+array[4];
        return finalText;
    }
    @RequestMapping(value = "/updateBook",method = RequestMethod.POST)
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


}
