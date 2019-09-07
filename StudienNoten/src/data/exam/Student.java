package data.exam;

import store.DataManagement;
import store.ExamStore;
import store.StoreException;

public class Student {

    private String username;
    private String password;
    private static DataManagement store;

    public Student(String username, String password, boolean newStudent) throws IllegalInputException, StoreException {
        setUsername(username);
        setPassword(password);
        store = ExamStore.instance();
        if (newStudent)
            store.newUser(username, password);
        if (!newStudent)
            store.setUser(username, password);
    }

    private void setUsername(String username) throws IllegalInputException {
        if (username.length() < 1)
            throw new IllegalInputException("At least two chars");
        this.username = username;
    }

    private void setPassword(String password) throws IllegalInputException {
        if (password.length() < 1)
            throw new IllegalInputException("At least two chars");
        this.password = password;
    }

}
