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
               System.out.println("number of available copies"+book.getNoOfAvailableCopies());
               book.setNoOfAvailableCopies(book.getNoOfAvailableCopies()-1);
               book.getNoOfAvailableCopies();
               session.update(book);

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

        for (TransactionBooks transactionBook:transactionBooksList) {

            int transactionBookId = transactionBook.getTransactionBooksId();
            //TransactionBooks transactionBooks = session.get(TransactionBooks.class,transactionBookId);
            System.out.println("Hereeeeeeeeeeeeeeeeee "+transactionBook.getDueDate());
            //if number of available copies was 0 then we have to take out the first patron in waitlist and assign the book to him

            if(transactionBook.getBook().getNoOfAvailableCopies()==0){
                ProcedureCall call = session
                        .createStoredProcedureCall("return_books");

                call.registerParameter(1, Long.class,
                        ParameterMode.IN).bindValue((long)transactionBook.getTransactionBooksId());



                Output output = call.getOutputs().getCurrent();
                if (output.isResultSet()) {
                    //List<Object[]> postComments =
                    //((ResultSetOutput) output).getResultList();
                }
                /*String getWaitlistPatron = "select w.user from Waitlist w where w.waitlistId = (select min(w2.waitlistId) from Waitlist w2 where w2.book=:book)";
                Query getWaitlistPatronQuery = (Query) session.createQuery(getWaitlistPatron);
                getWaitlistPatronQuery.setParameter("book",transactionBook.getBook());
                //System.out.println((int)retrieveQuery.getResultList().size()+"in transaction query result");
                System.out.println(getWaitlistPatronQuery.getResultList().get(0) + "only today");
                User u=(User)getWaitlistPatronQuery.getResultList().get(0);
                //if there are no patrons in waitlist for that book, increase the number of available copies by 1
                if(u==null){
                    transactionBook.setReturnDate(dateobj);

                    int bookId=transactionBook.getBook().getBookId();
                    Book book = session.get(Book.class,bookId);
                    book.setNoOfAvailableCopies(book.getNoOfAvailableCopies() + 1);
                    session.update(transactionBook);
                    session.update(book);
                }
                //take out the patron from waitlist table, assign the book to him by creating a record in waitlistToBeAssigned table
                else{
                    System.out.println("inside the waitlistbookstobeassigned");
                    Date date=appTime;
                    WaitlistBooksToBeAssigned obj=new WaitlistBooksToBeAssigned(transactionBook.getTransaction().getUser(),transactionBook.getBook(),date);
                    session.save(obj);
                    session.delete(u);
                }*/
                //Long todayBooks = (Long) getWaitlistPatronQuery.getResultList().get(0);

            }
            //if the number of available copies was greater than 0, then just increase the number of available copies straight away
            else {
                transactionBook.setReturnDate(dateobj);

                int bookId = transactionBook.getBook().getBookId();
                Book book = session.get(Book.class, bookId);
                book.setNoOfAvailableCopies(book.getNoOfAvailableCopies() + 1);
                session.update(transactionBook);
                session.update(book);
            }
        }
        mailService.sendTransactionReturnsInfoMail(transactionBooksList,(User)session.get(User.class,userId),dateobj);

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
       // java.sql.Date appTimeSqlFormat = new java.sql.Date(appTime.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(appTime);
        c.add(Calendar.DATE, 5);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        System.out.println("inside checkForDueDates");
        //checking if someone just passed the due date and no mail has been sent to him/her yet
        String firstRemainderMail="select t.user,tb from TransactionBooks tb join tb.transaction t "+
                " where tb.returnDate is null and tb.dueDate < :appTime and tb.lastReminderMailTime is null";
        Query query=(Query)session.createQuery(firstRemainderMail);
        query.setParameter("appTime", c.getTime());
        List<Object[]> list=(List<Object[]>) query.getResultList();
        for(Object[] obj:list){
            User user=(User) obj[0];
            TransactionBooks transactionBook=(TransactionBooks) obj[1];
            System.out.println(user+"printing"+transactionBook.getBook().getTitle()+transactionBook.getDueDate());

            //set the last remainder mail column to the transactionBook object so that we don't send him mail again in the same day
            transactionBook.setLastReminderMailTime(appTime);
            session.update(transactionBook);
            //sending the mail
            mailService.sendReminderMail(user,transactionBook);
        }
        //checking if someone has passed the due date and sending reminder mails to him has already started.
        String followingRemainderMails="select t.user,tb from TransactionBooks tb join tb.transaction t"+
                " where tb.returnDate is null and tb.dueDate < :appTime and day(tb.lastReminderMailTime)<day(:appTime)";
        /*String followingRemainderMails="select t.user,tb from TransactionBooks tb join tb.transaction t"+
                " where tb.returnDate is null and tb.dueDate < current_date and " +
                " (day(tb.dueDate)*24*60*60+hour(tb.dueDate)*60*60+minute(tb.dueDate)*60+" +
                " second(tb.dueDate)-day(:appTime)*24*60*60-hour(:appTime)*60*60-" +
                "minute(:appTime)*60- second(:appTime))";*/
        Query query2=(Query)session.createQuery(followingRemainderMails);
        query2.setParameter("appTime", c.getTime());
        List<Object[]> list2=(List<Object[]>) query2.getResultList();
        for(Object[] obj:list2){
            User user=(User) obj[0];
            TransactionBooks transactionBook=(TransactionBooks) obj[1];
            System.out.println("in the following remainder mails");
            System.out.println(user+"printing"+transactionBook.getBook().getTitle()+transactionBook.getDueDate());
            Date date=new Date();
            //updating the new reminder mail time
            transactionBook.setLastReminderMailTime(date);
            session.update(transactionBook);
            //sending the mail
            mailService.sendReminderMail(user,transactionBook);
        }
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void checkForWaitlistAssignments() {
        ApplicationTime a = new ApplicationTime();
        Date appTime = ApplicationTime.staticAppDateTime;
       // java.sql.Date appTimeSqlFormat = new java.sql.Date(appTime.getTime());

        System.out.println("Timeeeeeeeeeeeeeeeeeeeeeeeeeeeee "+ appTime);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //check for the patrons who didn't checkout the books assigned to them within 3 days
        String waitlistAssignments="select wb from WaitlistBooksToBeAssigned wb where  wb.isInvalid=false and (day(:appDate)*24*60+hour(:appDate)*60+minute(:appDate)"+
                "-day(wb.currentDate)-hour(wb.currentDate)-minute(wb.currentDate))>4320 ";
        Query waitlistAssignmentsQuery=(Query)session.createQuery(waitlistAssignments);
       // waitlistAssignmentsQuery.setParameter("appDate", appTimeSqlFormat.toString());
        waitlistAssignmentsQuery.setParameter("appDate", appTime);

        System.out.println("Queryyyyyyyyyyyyyyyyyyyyyy "+ waitlistAssignmentsQuery);

        List<WaitlistBooksToBeAssigned> waitlistBooksToBeAssigned=(List<WaitlistBooksToBeAssigned>)waitlistAssignmentsQuery.getResultList();
        //loop through all such records in waitlistBooksToBeAssigned table(patrons who didn't checkout their corresponding assigned books in 3 days)
        for (WaitlistBooksToBeAssigned wb:waitlistBooksToBeAssigned) {
            //System.out.println(wb.getWaitlistBooksToBeAssignedId());
            //get the book he is assigned
            Book book=wb.getBook();
            //find the next patron in waitlist table for that book
            String nextPatronInWaitlist="select w from Waitlist w where w.waitlistId = (select min(w2.waitlistId) from Waitlist w2 where w2.book=:book)";
            Query nextPatronInWaitlistQuery = (Query) session.createQuery(nextPatronInWaitlist);
            nextPatronInWaitlistQuery.setParameter("book",wb.getBook());
            //System.out.println((int)retrieveQuery.getResultList().size()+"in transaction query result");
            System.out.println(nextPatronInWaitlistQuery.getResultList().get(0) + "inside checkForWaitlistAssignments next man in waitlist");
            Waitlist w=(Waitlist)nextPatronInWaitlistQuery.getResultList().get(0);
            //if there is no one in the wait list for that book, delete the record from waitlistToBeAssigned table and increment the number of available copies by 1
            if(w==null){
                Book book1=wb.getBook();
                book1.setNoOfAvailableCopies(book1.getNoOfAvailableCopies()+1);
                session.delete(wb);

            }
            //if there is any one in waitlist, take him out of waitlist, add to the waitlistToBeAssigned table, delete the old record from the table and waitlist tbale
            else {
                User u = w.getUser();
                WaitlistBooksToBeAssigned waitlistBooksToBeAssigned1 = new WaitlistBooksToBeAssigned(u, wb.getBook(), new Date());
                session.save(waitlistBooksToBeAssigned1);
                session.delete(w);
                session.delete(wb);
            }
        }
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
