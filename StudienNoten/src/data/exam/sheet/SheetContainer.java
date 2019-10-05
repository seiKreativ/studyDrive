package data.exam.sheet;

import java.util.Iterator;
import java.util.Vector;

import store.ExamStore;
import store.StoreException;

public class SheetContainer implements Iterable<Sheet> {

    private static SheetContainer unique = null;
    private Vector<Sheet> sheets;
    private ExamStore store = null;

    private SheetContainer() throws StoreException {
        sheets = new Vector<Sheet>();
        store = ExamStore.instance();
    }

    public static SheetContainer instance() throws StoreException {
        if (unique == null) {
            unique = new SheetContainer();
        }
        return unique;
    }

    public void linkSheet(Sheet e) throws SheetAlreadyExistsException, StoreException {
        boolean temp = false;
        for (Sheet etemp : sheets) {
            if (etemp.equals(e)) {
                temp = true;
                break;
            }
        }
        if (temp)
            throw new SheetAlreadyExistsException(e.getName());
        store.addSheet(e);
        sheets.add(e);
    }

    public void unlinkSheet(Sheet e) throws SheetNotFoundException, StoreException {
        boolean temp = false;
        for (Sheet etemp : sheets) {
            if (etemp.equals(e)) {
                temp = true;
                break;
            }
        }
        if (!temp)
            throw new SheetNotFoundException(e.getName());
        store.deleteSheet(e);
        sheets.remove(e);
    }

    public void linkSheetLoading(Sheet e) throws SheetAlreadyExistsException {
        if (sheets.contains(e))
            throw new SheetAlreadyExistsException(e.getName());
        sheets.add(e);
    }

    /*
     * the actuall modifying has to happen in the gui panel with the setters
     */
    public void modify(Sheet eold, Sheet enew) throws StoreException {
        store.modifySheet(eold, enew);
    }

    public Sheet getSheetByIndex(int pos) {
        return sheets.get(pos);
    }

    public Sheet getSheetByName(String name, int semester) {
        for (Sheet e : sheets) {
            if (e.getName().equals(name) && e.getSemester() == semester)
                return e;
        }
        return null;
    }

    public int getMaxOtherNumber() {
        int tmp = 0;
        for (Sheet e : sheets) {
            if (e.getType() == Sheet.OTHER_TYPE) {
                if (e.getNumber() > tmp)
                    tmp = e.getNumber();
            }
        }
        return tmp;
    }

    public Vector<Sheet> getAllExams() {
        return this.sheets;
    }

    public Iterator<Sheet> iterator() {
        return sheets.iterator();
    }

    public int getSize() {
        return sheets.size();
    }

    public void close() {
        unique = null;
    }

}
