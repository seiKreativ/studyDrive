package store;

import data.exam.Exam;
import data.exam.ExamContainer;

public interface DataManagement {

    public void load(ExamContainer container) throws StoreException;

    /*
    for the regristration of a new User
    */
    public void newUser(String username, String password) throws StoreException;

    /*
    sets the User (at login)
    */
    public void setUser(String username, String password) throws StoreException;

    public void add(Exam e) throws StoreException;

    public void delete(Exam e) throws StoreException;

    public void modify(Exam eold, Exam enew) throws StoreException;

    public void close() throws StoreException;

}