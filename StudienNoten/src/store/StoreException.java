package store; 

@SuppressWarnings("serial")
public class StoreException extends Exception {
	public StoreException(String message, Exception cause) {
		super(message, cause);
	}

	public String getDetails() {
		if (getCause() == null)
			return "no details";
		else
			return getCause().getClass() + ", " + getCause().getMessage();
	}
}
