package com.lms.cmpe.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nischith on 12/2/2016.
 */
public class ApplicationTime {

    private Date appDateTime;


    public ApplicationTime(String appDateTimeInString) {
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

    public void setAppDateTime(Date appDateTime) {
        this.appDateTime = appDateTime;
    }




}
