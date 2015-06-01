package exceptions;

public class UnsupportedOperationException extends Exception {
	
	private static final long serialVersionUID = -5945790125809097740L;

	public UnsupportedOperationException(String message) {
        super(message);
    }
	
	public UnsupportedOperationException(Throwable throwable) {
        super(throwable);
    }
	
	public UnsupportedOperationException(String message, Throwable throwable) {
        super(message, throwable);
    }
	
}