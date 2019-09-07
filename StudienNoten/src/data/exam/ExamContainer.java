package data.exam;

import java.util.Iterator;
import java.util.Vector;

import store.ExamStore;
import store.StoreException;

public class ExamContainer implements Iterable<Exam> {
	private static ExamContainer unique = null;
	private Vector<Exam> exams;
	private ExamStore store = null;

	private ExamContainer() throws StoreException {
		exams = new Vector<Exam>();
		store = ExamStore.instance();
	}

	public static ExamContainer instance() throws StoreException {
		if (unique == null)
			unique = new ExamContainer();
		return unique;
	}

	public void linkExam(Exam e) throws ExamAlreadyExistsException, StoreException {
		if (exams.contains(e))
			throw new ExamAlreadyExistsException(e.getName());
		store.add(e);
		exams.add(e);
	}

	public void unlinkExam(Exam e) throws ExamNotFoundException, StoreException {
		if (!exams.contains(e))
			throw new ExamNotFoundException(e.getName());
		store.delete(e);
		exams.remove(e);
	}

	public void linkExamLoading(Exam e) throws ExamAlreadyExistsException {
		if (exams.contains(e))
			throw new ExamAlreadyExistsException(e.getName());
		exams.add(e);
	}

	/*
	the actuall modifying has to happen in the gui panel with the setters
	 */
	public void modify(Exam eold, Exam enew) throws StoreException {
		store.modify(eold, enew);
	}

	public void loadExams() throws StoreException {
		store.load(unique);
		/*
		not needed anymore isn't it?

		Vector<Exam> backup = new Vector<Exam>(exams);
		exams.clear();
		try {
			store.load(unique);
		} catch (StoreException e) {
			exams = backup;
			throw e;
		}
		*/
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

	public void close() throws StoreException {
		store.close();
	}
}
