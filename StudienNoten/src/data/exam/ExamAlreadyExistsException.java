package data.exam;

public class ExamAlreadyExistsException extends Exception {
	public ExamAlreadyExistsException(String name) {
		super("Existing exam with id number " + name);
	}
}
