package org.cc11001100.web.test.exception;

/**
 * 
 * 到达设定的最大尝试次数的时候会抛出此异常
 * 
 * @author chenjc20326
 *
 */
public class TheBiggestAttemptsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TheBiggestAttemptsException() {
	}
	
	public TheBiggestAttemptsException(String mesg) {
		super(mesg);
	}
	
	public TheBiggestAttemptsException(Throwable cause) {
		super(cause);
	}
	
	public TheBiggestAttemptsException(String mesg, Throwable cause) {
		super(mesg, cause);
	}
	
}
