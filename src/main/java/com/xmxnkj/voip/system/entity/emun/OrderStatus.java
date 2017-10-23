package com.xmxnkj.voip.system.entity.emun;

//订单状态
public enum OrderStatus {
	Request,			//下单请求阶段
	RequestFinishing,	//下单部分完成阶段
	RequestFinished,	//下单完成
	closeRequest,		//平仓申请中
	closeFinished,		//平仓完成
	
	fail,				//下单失败
	closeFail			//平仓失败
}
