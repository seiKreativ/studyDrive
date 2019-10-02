package data.exam;

import store.DataManagement;
import store.ExamStore;
import store.StoreException;

public class Student {

    private String email;
    private String password;
    private static DataManagement store;

    public Student(String name, String email, String password, boolean newStudent) throws IllegalInputException, StoreException {
        setEmail(email);
        setPassword(password);
        store = ExamStore.instance();
        if (newStudent)
            store.newUser(name, email, password);
        if (!newStudent)
            store.setUser(email, password);
    }

    private void setEmail(String email) throws IllegalInputException {
        if (email.length() < 1)
            throw new IllegalInputException("At least two chars");
        this.email = email;
    }

    private void setPassword(String password) throws IllegalInputException {
        if (password.length() < 1)
            throw new IllegalInputException("At least two chars");
        this.password = password;
    }

}
