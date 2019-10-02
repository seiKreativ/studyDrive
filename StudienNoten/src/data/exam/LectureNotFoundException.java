package data.exam;

public class LectureNotFoundException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 8171924558106532929L;

    public LectureNotFoundException(String name) {
        super("There is no exam with name " + name);
    }
}
