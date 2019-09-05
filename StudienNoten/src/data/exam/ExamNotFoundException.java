package data.exam;

public class ExamNotFoundException extends Exception {
	public ExamNotFoundException(String name) {
		super("There is no exam with name " + name);
	}
}
