package store;

import data.exam.Exam;
import data.exam.ExamContainer;

public interface DataManagement {
    public void load(ExamContainer container) throws StoreException;

    public void save(ExamContainer container) throws StoreException;

    public void add(Exam e) throws StoreException;

    public void delete(Exam e) throws StoreException;

    public void modify(Exam e) throws StoreException;

    public void close();

}

