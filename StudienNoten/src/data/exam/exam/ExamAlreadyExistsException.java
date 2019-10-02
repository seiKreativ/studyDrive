package data.exam.exam;

public class ExamAlreadyExistsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5043529967317856303L;

	public ExamAlreadyExistsException(String name) {
		super("Existing exam with id number " + name);
	}
}
