package com.lms.cmpe.dao;

import com.lms.cmpe.model.*;
import com.lms.cmpe.service.LmsException;
import com.lms.cmpe.service.MailService;
import javassist.bytecode.stackmap.BasicBlock;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.result.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static javafx.scene.input.KeyCode.O;
import static org.springframework.orm.hibernate3.SessionFactoryUtils.getSession;


/**
 * Created by Nischith on 11/28/2016.
 */
@Repository
public class TransactionDaoImpl implements TransactionDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MailService mailService;



    @Override
    @SuppressWarnings("unchecked")
    public Transaction checkOutBooks(Transaction transaction, int userId,Date appTime) {

        System.out.println(appTime+"from transactionDAO");
       try {
           Session session = sessionFactory.openSession();

           session.beginTransaction();
           String retrieve = "select count(*) from TransactionBooks tb join tb.transaction t" +
                   " where tb.returnDate" +
                   " is null and t.user.userId=:userId and day(t.transactionDate)=day(:appTime)";
           Query retrieveQuery = (Query) session.createQuery(retrieve);
           retrieveQuery.setParameter("userId", userId);
           retrieveQuery.setParameter("appTime",appTime);
           //System.out.println((int)retrieveQuery.getResultList().size()+"in transaction query result");
           System.out.println(retrieveQuery.getResultList() + "only today");

           Long todayBooks = (Long) retrieveQuery.getResultList().get(0);
           int temp = transaction.getTransactionBooksList().size();
           if (todayBooks + temp > 5) {
               System.out.println(todayBooks + temp + "the number of bookks today");
               throw new LmsException("maximumNoOfBooksPerDayViolated");//TODO: Akash implement custom exception page from the UI
           }
           String r2 = "select count(*) from TransactionBooks tb join tb.transaction t" +
                   " where tb.returnDate" +
                   " is null and t.user.userId=:userId";
           Query q2 = (Query) session.createQuery(r2);
           q2.setParameter("userId", userId);
           System.out.println(q2.getResultList() + "all days");
           Long allBooks = (Long) q2.getResultList().get(0);
           if (allBooks + temp > 10) {
               System.out.println(allBooks + temp + "number of books all time");
               throw new LmsException("maximumNoOfBooksViolated");//TODO: Akash implement custom exception page from the UI
           }
           for (TransactionBooks transactionBook:transaction.getTransactionBooksList()) {
               int bookId=transactionBook.getBook().getBookId();
               Book book = session.get(Book.class,bookId);
               //User user=session.get(User.class,userId);
               String checkBookInWaitlistToBeAssigned="select wb.waitlistBooksToBeAssignedId from WaitlistBooksToBeAssigned wb where wb.user.userId=:userId and wb.book.bookId=:bookId";
               Query q_checkBookInWaitlistToBeAssigned=(Query) session.createQuery(checkBookInWaitlistToBeAssigned);
               q_checkBookInWaitlistToBeAssigned.setParameter("userId",userId);
               q_checkBookInWaitlistToBeAssigned.setParameter("bookId",bookId);
               int count=q_checkBookInWaitlistToBeAssigned.getResultList().size();
               if(count>0){
                   //session.delete(waitlistBooksToBeAssignedList.get(0));
                   int waitlistBooksToBeAssignedId=(int)q_checkBookInWaitlistToBeAssigned.getResultList().get(0);
                   ProcedureCall call = session
                           .createStoredProcedureCall("delete_waitlist_to_be_assigned");

                   call.registerParameter(1, int.class,
                           ParameterMode.IN).bindValue((waitlistBooksToBeAssignedId));

                   Output output = call.getOutputs().getCurrent();

               }else {
                   System.out.println("number of available copies" + book.getNoOfAvailableCopies());
                   book.setNoOfAvailableCopies(book.getNoOfAvailableCopies() - 1);
                   book.getNoOfAvailableCopies();
                   session.update(book);
               }

           }
           session.save(transaction);
           session.getTransaction().commit();
           session.close();
           return transaction;
       }catch (LmsException e){
           System.out.println(e);
           return null;
       }
    }

    @Override
    public boolean returnBooks(ArrayList<TransactionBooks> transactionBooksList, int userId,Date appTime){

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Date dateobj = appTime;
        ArrayList fineList = new ArrayList();
        for (TransactionBooks transactionBook:transactionBooksList) {

            int transactionBookId = transactionBook.getTransactionBooksId();
            //TransactionBooks transactionBooks = session.get(TransactionBooks.class,transactionBookId);
            System.out.println("Hereeeeeeeeeeeeeeeeee "+transactionBook.getDueDate());
            //if number of available copies was 0 then we have to take out the first patron in waitlist and assign the book to him

                Date testDate=new Date();
                ProcedureCall call = session
                        .createStoredProcedureCall("return_books");

                call.registerParameter(1, Long.class,
                        ParameterMode.IN).bindValue((long)transactionBook.getTransactionBooksId());

                call.registerParameter(2, Date.class,
                    ParameterMode.IN).bindValue(testDate);

                Output output = call.getOutputs().getCurrent();
                if (output.isResultSet()) {

                }
                long diffInDays =  ((appTime.getTime() - transactionBook.getDueDate().getTime()) / (1000 * 60 * 60 * 24));
                //i f(transactionBook.getDueDate())

                fineList.add(diffInDays);

                System.out.println("checking if return date is updated"+diffInDays);
        }
        mailService.sendTransactionReturnsInfoMail(transactionBooksList,fineList,(User)session.get(User.class,userId),dateobj);

        //session.save(transaction);
        session.getTransaction().commit();
        session.close();
        return false;
    }

    @Override
    public List<Book> getCheckedOutBooksByUser(int userId){

        Session session = sessionFactory.openSession();
        String retrieve = "SELECT tb.transactionBooksId FROM TransactionBooks tb join tb.transaction t where " +
                "tb.transactionBooksId = t.transactionId and t.user.userId = :userId ";
        //     "and t.transactionDate = sysdate() "+
        //     "and tb.returnDate is null";
        Query retrieveQuery = (Query) session.createQuery(retrieve);
        retrieveQuery.setParameter("userId",userId);
        int count =(Integer)retrieveQuery.getSingleResult();
        System.out.println("Inside queryyyyyyyyyyyyyy " + count);
        session.close();
        return null;
    }

    @Override
    //unchecked
    public List<TransactionBooks> getBooksToBeReturned(int userId) {
        Session session=sessionFactory.openSession();
        String retrieve="select tb from TransactionBooks tb join tb.transaction t" +
                " where tb.returnDate" +
                " is null and t.user.userId=:userId";
        Query query = (Query) session.createQuery(retrieve);
        query.setParameter("userId", userId);
        System.out.println(query.getResultList());// todo:// here you get the list of transactionbooks objects which include all the book details and due date, fine etc., off the books taken by a user
        List<TransactionBooks> list = (List<TransactionBooks>)query.getResultList();

        session.close();
        return list;
    }

    //cron job for checking if any patron hasn't returned the book after duedate
    @Override
    public void checkForDueDates(){
        Date appTime = ApplicationTime.staticAppDateTime;
        if(appTime == null)
        {
            appTime = new Date();
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        System.out.println("inside checkForDueDates");
        //checking if someone just passed the due date and no mail has been sent to him/her yet
        String firstRemainderMail="select t.user,tb from TransactionBooks tb join tb.transaction t "+
                " where tb.returnDate is null and tb.lastReminderMailTime is null";
        Query query=(Query)session.createQuery(firstRemainderMail);
        List<Object[]> list=(List<Object[]>) query.getResultList();
        for(Object[] obj:list){
            User user=(User) obj[0];
            TransactionBooks transactionBook=(TransactionBooks) obj[1];

            Date dueDate = transactionBook.getDueDate();

            int diffInDays = (int) ((appTime.getTime() - dueDate.getTime()) / (1000 * 60 * 60 * 24));
            System.out.println("dueDate.getTime() "+dueDate);
            System.out.println("appTime.getTime() "+appTime);
            System.out.println("diffInDaysssssssssssssssssssss "+diffInDays);
            if(diffInDays > 5)
            {
                //set the last remainder mail column to the transactionBook object so that we don't send him mail again in the same day
                transactionBook.setLastReminderMailTime(appTime);
                session.update(transactionBook);
                //sending the mail
                mailService.sendReminderMail(user,transactionBook);
            }
        }
        //checking if someone has passed the due date and sending reminder mails to him has already started.
        String followingRemainderMails="select t.user,tb from TransactionBooks tb join tb.transaction t"+
                " where tb.returnDate is null";

        Query query2=(Query)session.createQuery(followingRemainderMails);

        List<Object[]> list2=(List<Object[]>) query2.getResultList();
        for(Object[] obj:list2){
            User user=(User) obj[0];
            TransactionBooks transactionBook=(TransactionBooks) obj[1];


            Date dueDate = transactionBook.getDueDate();
            Date lastReminderMailDate = transactionBook.getLastReminderMailTime();
            int diffInDays = (int) ((dueDate.getTime() - appTime.getTime()) / (1000 * 60 * 60 * 24));
            int differenceInLastReminderMailDate = 0;
            try{
                differenceInLastReminderMailDate = (int) ((lastReminderMailDate.getTime() - appTime.getTime()) / (1000 * 60 * 60 * 24));
            }catch (Exception e)
            {
            }

            if(diffInDays > 5 && differenceInLastReminderMailDate > 0 )
            {
                //updating the new reminder mail time
                transactionBook.setLastReminderMailTime(appTime);
                session.update(transactionBook);
                //sending the mail
                mailService.sendReminderMail(user,transactionBook);
            }
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void checkForWaitlistAssignments() {

        Date appTime = ApplicationTime.staticAppDateTime;
        if(appTime == null)
        {
            appTime = new Date();
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        ProcedureCall call = session
                .createStoredProcedureCall("check_for_waitlist_assignments");

        call.registerParameter(1, Date.class,
                ParameterMode.IN).bindValue(appTime);


        call.execute();


        session.getTransaction().commit();
        session.close();

    }

    @Override
    public int reissueBook(int transactionBookId, int userId){
        int returnValue = 0;
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        TransactionBooks transactionBook = session.get(TransactionBooks.class, transactionBookId);
        int bookId = transactionBook.getBook().getBookId();

        Book book = session.get(Book.class, bookId);

        String retrieve = "SELECT wl.waitlistId FROM Waitlist wl where wl.book = :book ";
        int count = 0;
        try{
            Query retrieveQuery = (Query) session.createQuery(retrieve);
            retrieveQuery.setParameter("book",book);
            count = (Integer)retrieveQuery.getSingleResult();
        }
        catch (NoResultException e){
            System.out.println("Exception ");
        }
        catch(Exception e)
        {
            System.out.println("Exception ");
        }


        try {
            if (count > 0) {
                System.out.println("Cannot issueeeeeeeeeeeeee as it is requested by someone else ");
                returnValue = 1;
                throw new LmsException("reissueFailedAsBookRequestedBySomeone");
            } else if (transactionBook.getNoOfTimesRenewed() >= 2) {
                returnValue = 2;
                System.out.println("Cannot issueeeeeeeeeeeeee as it is requested by someone else ");

                throw new LmsException("reissueFailedAsBookIssuedTwice");
            } else {
                Date dueDate = transactionBook.getDueDate();
                Calendar c = Calendar.getInstance();
                c.setTime(dueDate);
                c.add(Calendar.DATE, 30);
                transactionBook.setNoOfTimesRenewed(transactionBook.getNoOfTimesRenewed() + 1);
                transactionBook.setDueDate(c.getTime());
            }
        }catch(LmsException e)
        {
            System.out.println(e);
            System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrr " + returnValue);

            return returnValue;
        }
        session.getTransaction().commit();
        session.close();

        return returnValue;
    }
}
