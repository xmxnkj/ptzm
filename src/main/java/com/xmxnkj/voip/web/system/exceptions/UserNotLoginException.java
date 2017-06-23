package com.xmszit.voip.web.system.exceptions;

import com.hsit.common.exceptions.ApplicationException;

/**
 * @ProjectName:voip
 * @ClassName: UserNotLoginException
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class UserNotLoginException extends ApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 534026202973535121L;

	/**
	 * 
	 */
	public UserNotLoginException() {
		// TODO Auto-generated constructor stub
	}
	
	public UserNotLoginException(String paramString) {
		// TODO Auto-generated constructor stub
		super(paramString);
	}
	
	public UserNotLoginException(Throwable throwable) {
		// TODO Auto-generated constructor stub
		super(throwable);
	}
	
	public UserNotLoginException(String param, Throwable throwable) {
		// TODO Auto-generated constructor stub
		super(param, throwable);
	}
}
