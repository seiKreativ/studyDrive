package data.exam;

public class ExamNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8171924558106532929L;

	public ExamNotFoundException(String name) {
		super("There is no exam with name " + name);
	}
}
