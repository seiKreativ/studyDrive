package data.exam;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import store.ExamStore;
import store.StoreException;



public class ExamContainer implements Iterable<Exam> {
	private static ExamContainer unique = null;
	private Vector<Exam> exams;
	private ExamStore store = null;

	private ExamContainer() {
		exams = new Vector<Exam>();
	}

	public static ExamContainer instance() {
		if (unique == null)
			unique = new ExamContainer();
		return unique;
	}

	public void linkExam(Exam e) throws ExamAlreadyExistsException {
		if (exams.contains(e))
			throw new ExamAlreadyExistsException(e.getName());
		exams.add(e);
	}

	public void unlinkExam(Exam e) throws ExamNotFoundException {
		if (!exams.contains(e))
			throw new ExamNotFoundException(e.getName());
		exams.remove(e);
	}

	public void loadExams(File file) throws StoreException {
		store = new ExamStore(file);
		Vector<Exam> backup = new Vector<Exam>(exams);
		exams.clear();
		try {
			store.load(unique);
		} catch (StoreException e) {
			exams = backup;
			throw e;
		}
	}

	public void saveParcels(File file) throws StoreException {
		store = new ExamStore(file);
		store.save(unique);
	}

	public Exam getExamByIndex(int pos) {
		return exams.get(pos);
	}

	public Exam getExamByName(String name) {
		for (Exam e : exams) {
			if (e.getName().equals(name))
				return e;
		}
		return null;
	}

	public Iterator<Exam> iterator() {
		return exams.iterator();
	}
}
