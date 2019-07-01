package org.akj.hystrix.service.exception;

public class RemoteServiceException extends RuntimeException {

	public RemoteServiceException(String message){
		super(message);
	}

}
