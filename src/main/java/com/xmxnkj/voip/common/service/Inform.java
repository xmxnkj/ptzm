package com.xmxnkj.voip.common.service;

import com.hsit.common.uac.entity.User;

/**
 * 
 * @author zjx
 *
 */
public interface Inform {
	void informUser(User user, String content);
}
