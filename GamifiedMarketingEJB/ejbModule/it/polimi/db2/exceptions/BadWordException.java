package it.polimi.db2.exceptions;

public class BadWordException extends Exception {
	private static final long serialVersionUID = 1L;

	public BadWordException(String message) {
		super(message);
	}
}
