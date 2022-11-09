package by.rss.reader.exception;

public class InvalidFeedException extends Exception {
	private static final long serialVersionUID = 6852414676345403703L;

	public InvalidFeedException(String msg) {
		super(msg);
	}
	
	public InvalidFeedException(String msg, Exception e) {
		super(msg, e);
	}
}
