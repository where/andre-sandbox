package com.amm.nosql;

/**
 * Base NoSQL exception.
 * @amesar
 */
public class NoSqlException extends RuntimeException {

	public NoSqlException() {
	}

	public NoSqlException(String message) {
		super(message);
	}

	public NoSqlException(Throwable throwable) {
		super(throwable);
	}

	public NoSqlException(String message, Throwable throwable) {
		super(message,throwable);
	}
}
