package store;

import data.exam.exam.Exam;
import data.exam.exam.ExamContainer;
import data.exam.lecture.Lecture;
import data.exam.lecture.LectureContainer;
import data.exam.sheet.Sheet;
import data.exam.sheet.SheetContainer;

public interface DataManagement {

    public void newUser(String name, String email, String password) throws StoreException;

    public void setUser(String email, String password) throws StoreException;

    public boolean checkEmail(String email) throws StoreException;

    public String getUserEmail() throws StoreException;

    public String getUserName() throws StoreException;

    public String getPassword() throws StoreException;

    public void changePasswort(String newPassword) throws StoreException;

    public boolean checkActivation(String email) throws StoreException;

    public boolean checkActivationCode(String code) throws StoreException;

    public void setActivationCode(String code) throws StoreException;

    public void deleteUser(LectureContainer c1, ExamContainer c2, SheetContainer c3) throws StoreException;

    public void addExam(Exam e) throws StoreException;

    public void deleteExam(Exam e) throws StoreException;

    public void modifyExam(Exam eold, Exam enew) throws StoreException;

    void addLecture(Lecture e) throws StoreException;

    void deleteLecture(Lecture e) throws StoreException;

    void modifyLecture(Lecture eold, Lecture enew) throws StoreException;

    void load(LectureContainer lectures, ExamContainer exams, SheetContainer sheets) throws StoreException;

    void addSheet(Sheet s) throws StoreException;

    void deleteSheet(Sheet s) throws StoreException;

    void modifySheet(Sheet sold, Sheet snew) throws StoreException;

    public void close() throws StoreException;

}