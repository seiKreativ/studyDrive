package data.exam.sheet;

public class SheetAlreadyExistsException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 5043529967317856303L;

    public SheetAlreadyExistsException(String name) {
        super("Sheet already exists");
    }
}
