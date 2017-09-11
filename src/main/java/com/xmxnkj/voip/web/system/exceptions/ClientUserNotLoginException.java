package com.xmxnkj.voip.web.system.exceptions;

import com.hsit.common.exceptions.ApplicationException;

/**
 * @ProjectName:voip
 * @ClassName: ClientUserNotLoginException
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class ClientUserNotLoginException extends ApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8873209291855924601L;

	public ClientUserNotLoginException(){
		
	}
	
	public ClientUserNotLoginException(String paramsString){
		super(paramsString);
	}
	
	public ClientUserNotLoginException(Throwable throwable){
		super(throwable);
	}
	
	public ClientUserNotLoginException(String param, Throwable throwable){
		super(param, throwable);
	}
}
