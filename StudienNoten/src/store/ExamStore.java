package store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import data.exam.Exam;
import data.exam.ExamAlreadyExistsException;
import data.exam.ExamContainer;
import gui.mainFrame.IllegalInputException;

public class ExamStore implements DataManagement {

    private File filename;

    public ExamStore(File file) throws StoreException {
	if (file == null)
	    throw new StoreException("no filename", null);
	this.filename = file;
    }

    public void load(ExamContainer container) throws StoreException {
	try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(filename))) {
	    // container = (AngestellterContainer) reader.readObject();???(call by value)
	    ExamContainer temp = (ExamContainer) reader.readObject();
	    for (Exam a : temp) {
		String name = a.getName();
		try {
		    container.linkExam(a);
		} catch (ExamAlreadyExistsException e) {
		    System.out.println("Exam " + name + " cannot loaded (invalid data-values): " + e.getMessage());
		}
	    }
	} catch (IOException | ClassNotFoundException ex) {
	    throw new StoreException("Loading failed", ex);
	}
    }

    public void save(ExamContainer container) throws StoreException {
    	System.out.println(System.getProperty("user.dir")+"\\container.bin");
	try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir")+"\\container.bin"))) {
	    writer.writeObject(container);
	} catch (IOException ex) {
	    throw new StoreException("Saving failed", ex);
	}
    }

    public void add(Exam o) throws StoreException {
	System.out.println("Empty implementation");
    }

    public void delete(Exam o) throws StoreException {
	System.out.println("Empty implementation");
    }

    public void modify(Exam o) throws StoreException {
	System.out.println("Empty implementation");
    }

    public void close() {
	System.out.println("Empty implementation");
    }

}