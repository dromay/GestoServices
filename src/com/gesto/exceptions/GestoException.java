package com.gesto.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

public class GestoException extends Exception {
	
		
	public GestoException() {
		super();
	}
	
	public GestoException(String message) {
		this(message, null);		
	}
	
	public GestoException(Throwable cause) {
		this(null,cause);		
	}
	
	public GestoException(String message, Throwable cause) {
		super(message,cause);		
	}			
	
	public void printStackTrace() {
		if (getCause()!=null) {
			getCause().printStackTrace();
		} else {
			super.printStackTrace();
		}
	}
	

	public void printStackTrace(PrintStream s) {
		if (getCause()!=null) {
			getCause().printStackTrace(s);
		} else {
			super.printStackTrace(s);
		}
	}	
	
	public void printStackTrace(PrintWriter w) {
		if (getCause()!=null) {
			getCause().printStackTrace(w);
		} else {
			super.printStackTrace(w);
		}
	}	
}
