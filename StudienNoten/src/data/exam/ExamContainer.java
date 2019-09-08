package data.exam;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;
import java.util.Vector;

import store.ExamStore;
import store.StoreException;

public class ExamContainer implements Iterable<Exam> {
	private static ExamContainer unique = null;
	private Vector<Exam> exams;
	private ExamStore store = null;
	private PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private ExamContainer() throws StoreException {
		exams = new Vector<Exam>();
		store = ExamStore.instance();
		loadExams();
	}

	public static ExamContainer instance() throws StoreException {
		if (unique == null) {
			unique = new ExamContainer();  
		}
		return unique;
	}

	public void linkExam(Exam e) throws ExamAlreadyExistsException, StoreException {
		boolean temp = false;
		for (Exam etemp : exams) {
			if (etemp.equals(e)) {
				temp = true;
				break;
			}
		}
		if (temp)
			throw new ExamAlreadyExistsException(e.getName());
		store.add(e);
		exams.add(e);
		changes.firePropertyChange("exam added", null, e);
	}

	public void unlinkExam(Exam e) throws ExamNotFoundException, StoreException {
		boolean temp = false;
		for (Exam etemp : exams) {
			if (etemp.equals(e)) {
				temp = true;
				break;
			}
		}
		if (!temp)
			throw new ExamNotFoundException(e.getName());
		store.delete(e);
		exams.remove(e);
		changes.firePropertyChange("exam removed", e, null);
	}

	public void linkExamLoading(Exam e) throws ExamAlreadyExistsException {
		if (exams.contains(e))
			throw new ExamAlreadyExistsException(e.getName());
		exams.add(e);
	}

	/*
	 * the actuall modifying has to happen in the gui panel with the setters
	 */
	public void modify(Exam eold, Exam enew) throws StoreException {
		store.modify(eold, enew);
	}

	public void loadExams() throws StoreException {
		store.load(this);
		/*
		 * not needed anymore isn't it?
		 * 
		 * Vector<Exam> backup = new Vector<Exam>(exams); exams.clear(); try {
		 * store.load(unique); } catch (StoreException e) { exams = backup; throw e; }
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
	
	public Vector<Exam> getAllExams() {
		return this.exams; 
	}
 
	public Iterator<Exam> iterator() {
		return exams.iterator();
	}
	
	public int getSize() {
		return exams.size(); 
	}

	public void close() throws StoreException {
		store.close();
		store = null;
		unique = null;
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}
	
	

}
