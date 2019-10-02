package store;

import data.exam.Exam;
import data.exam.ExamContainer;
import data.exam.Lecture;
import data.exam.LectureContainer;

public interface DataManagement {

    public void newUser(String name, String username, String password) throws StoreException;

    public void setUser(String username, String password) throws StoreException;

    public String getUser() throws StoreException;

    public String getPassword() throws StoreException;

    public void deleteUser() throws StoreException;

    public void addExam(Exam e) throws StoreException;

    public void deleteExam(Exam e) throws StoreException;

    public void modifyExam(Exam eold, Exam enew) throws StoreException;

    void addLecture(Lecture e) throws StoreException;

    void deleteLecture(Lecture e) throws StoreException;

    void modifyLecture(Lecture eold, Lecture enew) throws StoreException;

    void load(LectureContainer lectures, ExamContainer exams) throws StoreException;

    public void close() throws StoreException;

}