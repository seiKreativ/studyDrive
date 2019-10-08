package data.exam.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import data.exam.exam.ExamContainer;
import data.exam.sheet.SheetContainer;
import store.UserInformationStore;
import store.StoreException;

public class LectureContainer implements Iterable<Lecture> {
    private static LectureContainer unique = null;
    private Vector<Lecture> lectures;
    private UserInformationStore store = null;
    private ExamContainer examContainer;
    private SheetContainer sheetContainer;

    private LectureContainer() throws StoreException {
        lectures = new Vector<Lecture>();
        store = UserInformationStore.instance();
        load();
    }

    public static LectureContainer instance() throws StoreException {
        if (unique == null) {
            unique = new LectureContainer();
        }
        return unique;
    }

    public void linkLecture(Lecture e) throws LectureAlreadyExistsException, StoreException {
        boolean temp = false;
        for (Lecture etemp : lectures) {
            if (etemp.equals(e)) {
                temp = true;
                break;
            }
        }
        if (temp)
            throw new LectureAlreadyExistsException(e.getName());
        store.addLecture(e);
        lectures.add(e);
    }

    public void unlinkLecture(Lecture e) throws LectureNotFoundException, StoreException {
        boolean temp = false;
        for (Lecture etemp : lectures) {
            if (etemp.equals(e)) {
                temp = true;
                break;
            }
        }
        if (!temp)
            throw new LectureNotFoundException(e.getName());
        store.deleteLecture(e);
        lectures.remove(e);
    }

    public void linkLectureLoading(Lecture e) {
        if (lectures.contains(e))
            return;
        lectures.add(e);
    }

    /*
     * the actuall modifying has to happen in the gui panel with the setters
     */
    public void modify(Lecture eold, Lecture enew) throws StoreException {
        store.modifyLecture(eold, enew);
    }

    public void load() throws StoreException {
        examContainer = ExamContainer.instance();
        sheetContainer = SheetContainer.instance();
        store.load(this, examContainer, sheetContainer);
    }

    public Lecture getLectureByIndex(int pos) {
        return lectures.get(pos);
    }

    public Lecture getLectureByName(String name, int semester) {
        for (Lecture e : lectures) {
            if (e.getName().equals(name) && e.getSemester() == semester)
                return e;
        }
        return null;
    }
    
    public ArrayList<Lecture> getLecturesByName(String name) {
    	ArrayList<Lecture> lecs = new ArrayList<Lecture>(); 
        for (Lecture e : lectures) {
            if (e.getName().equals(name))
                lecs.add(e);
        }
        return lecs;
    }

    public Vector<Lecture> getAllLectures() {
        return this.lectures;
    }

    public Iterator<Lecture> iterator() {
        return lectures.iterator();
    }

    public int getSize() {
        return lectures.size();
    }

    public void close() throws StoreException {
        store.close();
        store = null;
        examContainer.close();
        sheetContainer.close();
        unique = null;
    }

    public String getUserEmail() throws StoreException {
        return store.getUserEmail();
    }

    public String getUserName() throws StoreException {
        return store.getUserName();
    }

    public String getPassword() throws StoreException {
        return store.getPassword();
    }

    public void setNewMail(String mail) throws StoreException {
        store.setNewMail(mail);
    }

    public boolean checkMailAreadyExists(String email) throws StoreException {
        return store.checkMailAreadyExists(email);
    }

    public void setActivated() throws StoreException {
        store.setActivated();
    }

    public void deleteUser() throws StoreException {
        store.deleteUser(this, examContainer, sheetContainer);
    }
    
    public void changePassword(String newPassword) throws StoreException {
        store.changePasswort(newPassword);
    }

    public boolean checkActivationCode(String code) throws StoreException {
        return store.checkActivationCode(code);
    }

    public String getUsercode() throws StoreException {
        return store.getUsercode();
    }
    public String getUserDate() throws StoreException {
		// TODO Auto-generated method stub
		return store.getUserDate();
	}


	public boolean contains(Lecture l) {
		for (int i = 0; i < this.getSize(); i++) {
			Lecture temp = this.getLectureByIndex(i); 
			if (l.getName().equals(temp.getName()) && l.getSemester() == temp.getSemester()) {
				return true; 
			}
		}
		return false; 
	}

}
