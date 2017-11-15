package com.xmxnkj.voip.system.ctp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.xmxnkj.jctp.trader.MarketCallBack;

import test.thostmduserapi.CThostFtdcDepthMarketDataField;

@Service
public class marketCallBackImpl implements MarketCallBack{
	
	private long count = 0;
	
	static Logger logger = LoggerFactory.getLogger("business");
	
	@Autowired
    @Qualifier("transactionManager")
    protected PlatformTransactionManager txManager;
	
	
	//记录行情
	//刷新最新价
	@Override
	public void getMarketData(CThostFtdcDepthMarketDataField pDepthMarketData) {
		System.out.println("行情获取中···"+";次数："+count++);
		System.out.println("交易日：" + pDepthMarketData.getTradingDay());
		System.out.println("合约代码：" + pDepthMarketData.getInstrumentID());
		System.out.println("交易所代码：" + pDepthMarketData.getExchangeID());
		System.out.println("合约在交易所的代码：" + pDepthMarketData.getExchangeInstID());
		System.out.println("最新价：" + pDepthMarketData.getLastPrice());
		System.out.println("上次结算价：" + pDepthMarketData.getPreSettlementPrice());
		System.out.println("昨收盘：" + pDepthMarketData.getPreClosePrice());
		System.out.println("昨持仓量：" + pDepthMarketData.getPreOpenInterest());
		System.out.println("今开盘：" + pDepthMarketData.getOpenPrice());
		System.out.println("最高价：" + pDepthMarketData.getHighestPrice());
		System.out.println("最低价：" + pDepthMarketData.getLowestPrice());
		System.out.println("数量：" + pDepthMarketData.getVolume());
		System.out.println("成交金额：" + pDepthMarketData.getTurnover());
		System.out.println("持仓量：" + pDepthMarketData.getOpenInterest());
		System.out.println("今收盘：" + pDepthMarketData.getClosePrice());
		System.out.println("本次结算价：" + pDepthMarketData.getSettlementPrice());
		System.out.println("涨停板价：" + pDepthMarketData.getUpperLimitPrice());
		System.out.println("跌停板价：" + pDepthMarketData.getLowerLimitPrice());
		System.out.println("昨虚实度：" + pDepthMarketData.getPreDelta());
		System.out.println("今虚实度：" + pDepthMarketData.getCurrDelta());
		System.out.println("最后修改时间：" + pDepthMarketData.getUpdateTime());
		System.out.println("最后修改毫秒：" + pDepthMarketData.getUpdateMillisec());
	}
}
