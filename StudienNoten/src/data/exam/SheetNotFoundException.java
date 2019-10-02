package data.exam;

public class SheetNotFoundException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 8171924558106532929L;

    public SheetNotFoundException(String name) {
        super("There is no sheet with name " + name);
    }
}
