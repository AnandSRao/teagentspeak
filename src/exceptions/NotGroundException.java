package exceptions;

public class NotGroundException extends Exception {
	
	private static final long serialVersionUID = 8584726832181115045L;

	public NotGroundException(String message) {
        super(message);
    }
	
	public NotGroundException(Throwable throwable) {
        super(throwable);
    }
	
	public NotGroundException(String message, Throwable throwable) {
        super(message, throwable);
    }
	
}