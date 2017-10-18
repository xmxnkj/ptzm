package com.xmxnkj.voip.system.service;

import com.hsit.common.service.AppBaseService;
import com.xmxnkj.voip.system.entity.ClientUser;
import com.xmxnkj.voip.system.entity.ExitEntry;
import com.xmxnkj.voip.system.entity.query.ExitEntryQuery;
import com.xmxnkj.voip.web.models.ListJson;

public interface ExitEntryService extends AppBaseService<ExitEntry, ExitEntryQuery>{
	
	//入金
	public void EntryMoney(String userId,ExitEntry e) throws Exception;
	
	//出金
	public void outMoney(String userId,ExitEntry e) throws Exception;
	
	//出金申请
	public ListJson applyExitMoney(ClientUser clientUser,double exitri) throws Exception;
	
	//审核不通过返还金额
	public ListJson failExitMoney(ClientUser clientUser,ExitEntry e) throws Exception;
}
