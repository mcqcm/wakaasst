package edu.cqu.wakaasst.core;

public class WakaAsstException extends Exception {

	private static final long serialVersionUID = -5181800588832010641L;
	
	private WakaAsstError error;

	public WakaAsstException() { }

	public WakaAsstException(String message) {
		super(message);
	}

	public WakaAsstException(Throwable cause) {
		super(cause);
	}

	public WakaAsstException(String message, Throwable cause) {
		super(message, cause);
	}

	public WakaAsstException(WakaAsstError error) {
		super(error.getMessage());
		this.error = error;
	}
	
	public WakaAsstError getError(){
		return error;
	}
}
