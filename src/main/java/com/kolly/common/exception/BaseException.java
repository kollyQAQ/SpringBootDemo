package com.kolly.common.exception;

/**
 * @author leasenli
 * 
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = 1876679637422297988L;

	private long errCode;
	private String errMsg;
	private Throwable rootCause;

	public BaseException() {
		super("Exception in NIUREN");
	}

	public BaseException(long errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public BaseException(long errCode, Throwable rootCause) {
		super(rootCause.getMessage());
		this.errCode = errCode;
		this.errMsg = rootCause.getMessage();
	}

	public BaseException(long errCode, String errMsg, Throwable rootCause) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.rootCause = rootCause;
	}

	public long getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public Throwable getRootCause() {
		return rootCause;
	}

}
