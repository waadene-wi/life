package com.wy.life.exception;

public class MyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2482054514596660242L;
	protected MyException(String message) {
		super(message);
	}
	
	public static MyException byMsg(String message) {
		return new MyException(message);
	}

}
