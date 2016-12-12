package com.lms.cmpe.tasks;

import com.lms.cmpe.dao.BookDao;
import com.lms.cmpe.dao.TransactionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by raghu on 12/9/16.
 */
@Component
public class ScheduledTasks {

    @Autowired
    private TransactionDao transactionDao;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {

        //log.info("The time is now {}", dateFormat.format(new Date()));
        //transactionDao.checkForDueDates();
    }

    @Scheduled(fixedRate = 5000)
    public void checkForWaitlistAssignments() {

        //log.info("The time is now {}", dateFormat.format(new Date()));
        //transactionDao.checkForWaitlistAssignments();

    }


}
