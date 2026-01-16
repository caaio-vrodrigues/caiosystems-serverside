package com.example.caiosystems.customexception;

public class ConcurrentUserClientException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConcurrentUserClientException(String msg) {
		super(msg);
	}
}