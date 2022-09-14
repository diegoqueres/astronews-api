package net.diegoqueres.astronews.domain.exception;

import java.io.Serial;

public class BusinessException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 6591518903904640104L;

	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}    
    
}
