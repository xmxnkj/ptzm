package com.xmxnkj.voip.system.entity.emun;

public enum ExitOrEntryEnum {
	
	EntryApply,		//用户入金 (普通用户  会员单位)
	EntryPass,		//入金成功
	EntryNotPass,	//入金失败
	
	ExitApply,			//出金申请
	ExitApplyPass,		//申请通过
	ExitApplyNotPass,	//申请不通过
	
	ExitSuccess,	//出金成功
	ExitFail		//出金失败
}
