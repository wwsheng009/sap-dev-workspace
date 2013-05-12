package com.sapint.Utils;

@SuppressWarnings("serial")
public class SAPException extends Exception {
	public String ABAPException;

	public SAPException(String string) {
		// TODO Auto-generated constructor stub
	}

	public SAPException(String message, Exception InnerException) {
		this.ABAPException = "";
	}

}
