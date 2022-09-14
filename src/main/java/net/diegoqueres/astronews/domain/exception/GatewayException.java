package net.diegoqueres.astronews.domain.exception;

import java.io.Serial;

public class GatewayException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -2139462248635507518L;

	public GatewayException(Throwable cause) {
		super(cause);
	}

	public GatewayException(String message) {
		super(message);
	}
	
	public GatewayException(String message, Throwable cause) {
		super(message, cause);
	}    
    
}
