package data.exam;

public class LectureAlreadyExistsException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 5043529967317856303L;

    public LectureAlreadyExistsException(String name) {
        super("Lecture already exists");
    }
}
