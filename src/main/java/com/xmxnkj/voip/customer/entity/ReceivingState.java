package com.xmxnkj.voip.customer.entity;

public enum ReceivingState {
	//线路忙（占线）
	Busy,
	//空号
	Empty,
	//系统IO异常
	IOException,
	//接听后挂断
	HangUpAfAns,
	//无人接听
	NoAnswer,
	//接听后无人说话
	NoSpeakAfAns,
	//正常接听
	Answer
}
