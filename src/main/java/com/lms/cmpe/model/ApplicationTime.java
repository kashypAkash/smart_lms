package com.lms.cmpe.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nischith on 12/2/2016.
 */
public class ApplicationTime {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private LocalDateTime appDateTime = LocalDateTime.now();
    private LocalDateTime actualDateTime = LocalDateTime.now();

    public ApplicationTime(LocalDateTime appDateTime) {
        String dt = dtf.format(appDateTime);
        this.appDateTime = LocalDateTime.parse(dt,dtf);
        System.out.println("appDateTime " + this.appDateTime);
        System.out.println("actualDateTime " + actualDateTime);
    }



    public LocalDateTime getAppDateTime() {
        return appDateTime;

    }

    public void setAppDateTime(LocalDateTime appDateTime) {
        this.appDateTime = appDateTime;
    }

    public LocalDateTime getActualDateTime() {
        return actualDateTime;
    }

    public void setActualDateTime(LocalDateTime actualDateTime) {
        this.actualDateTime = actualDateTime;
    }


}
