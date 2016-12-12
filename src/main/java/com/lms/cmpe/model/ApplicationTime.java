package com.lms.cmpe.model;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nischith on 12/2/2016.
 */

@Component
public class ApplicationTime {

    private Date appDateTime;

    public ApplicationTime() {
    }

    public ApplicationTime(String appDateTimeInString) {
        if(appDateTimeInString==null){
            //return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(appDateTimeInString);
            System.out.println(date);

        } catch (ParseException e) {

            e.printStackTrace();
            //return false;
        }

        //return true;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
            appDateTime = formatter.parse(appDateTimeInString);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }



    public Date getAppDateTime() {
        return appDateTime;

    }

    public boolean setAppDateTime(String appDateTimeInString) {

        //this.appDateTime = appDateTime;
        if(appDateTimeInString==null){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(appDateTimeInString);
            System.out.println(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }


        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
            appDateTime = formatter.parse(appDateTimeInString);
            return true;
        }catch (ParseException e){
            e.printStackTrace();
            return false;
        }
    }




}
