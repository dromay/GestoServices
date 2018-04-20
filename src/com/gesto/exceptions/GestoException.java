package com.gesto.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

public class GestoException extends Exception {

	private String codeError = null;

	public GestoException() {
		super();

	}

	public String getCodeError() {
		return codeError;
	}

	public void setCodeError(String codeError) {
		this.codeError = codeError;
	}

	public GestoException(String message) {
		this(message, null);
	}

	public GestoException(Throwable cause) {
		this(null, cause);
	}

	public GestoException(String message, Throwable cause) {
		super(message, cause);
	}

	public void printStackTrace() {
		if (getCause() != null) {
			getCause().printStackTrace();
		} else {
			super.printStackTrace();
		}
	}

	public void printStackTrace(PrintStream s) {
		if (getCause() != null) {
			getCause().printStackTrace(s);
		} else {
			super.printStackTrace(s);
		}
	}

	public void printStackTrace(PrintWriter w) {
		if (getCause() != null) {
			getCause().printStackTrace(w);
		} else {
			super.printStackTrace(w);
		}
	}
}
