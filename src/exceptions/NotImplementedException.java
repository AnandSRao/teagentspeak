package exceptions;

public class NotImplementedException extends Exception {
	
	private static final long serialVersionUID = 8584726832181115045L;

	public NotImplementedException(String message) {
        super(message);
    }
	
	public NotImplementedException(Throwable throwable) {
        super(throwable);
    }
	
	public NotImplementedException(String message, Throwable throwable) {
        super(message, throwable);
    }
	
}