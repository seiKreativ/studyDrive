package data.exam.exam;

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
		store.addExam(e);
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
		store.deleteExam(e);
		exams.remove(e);
		changes.firePropertyChange("exam removed", e, null);
	}

	public void linkExamLoading(Exam e) throws ExamAlreadyExistsException {
		//gibt grad noch Probleme, weil die lecture und die exam tabelle noch nicht schön eingebunden ist
		//if (exams.contains(e))
		//	throw new ExamAlreadyExistsException(e.getName());
		exams.add(e);
	}

	/*
	 * the actuall modifying has to happen in the gui panel with the setters
	 */
	public void modify(Exam eold, Exam enew) throws StoreException {
		store.modifyExam(eold, enew);
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

	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}

	public void close() {
		unique = null;
	}

}