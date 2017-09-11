package com.xmxnkj.voip.common.exceptions;

import com.hsit.common.exceptions.ApplicationException;

/**
 * 
 * @author zjx
 *
 */
public class NoAceException extends ApplicationException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 59096563154696523L;

	public NoAceException() {
		super("用户没有权限！");
	}

	public NoAceException(String paramString) {
		super(paramString);
	}

	public NoAceException(Throwable paramThrowable) {
		super(paramThrowable);
	}

	public NoAceException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}
}
