package com.xmxnkj.voip.system.ctp;

import com.xmxnkj.jctp.trader.ThostSpiImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xmxnkj.voip.system.timer.TaskTimer;
import com.xmxnkj.jctp.trader.MarketCallBack;
import com.xmxnkj.jctp.trader.TraderCallback;
import com.xmxnkj.jctp.trader.TraderSpiImpl;

import test.thostmduserapi.CThostFtdcMdApi;
import test.thosttraderapi.CThostFtdcTraderApi;
import test.thosttraderapi.THOST_TE_RESUME_TYPE;

public class MyTrader {
	
	//行情请求地址
	final static String ctp1_ThostAddress = "tcp://180.168.146.187:10010";
	
	private static MyTrader instance;
	
	static Logger logger = LoggerFactory.getLogger("business");
	
	public static void createInstance(TraderCallback callback,MarketCallBack marketCallBack){
		try{
			
			if (instance==null) {
				instance = new MyTrader(callback,marketCallBack);
				instance.initalize();
				
				//instance.initalizeK();	//行情
			}else{
				instance.login();
			}

		}catch(Exception e){
			
		}
	}
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	
	public static MyTrader getInstance(){
		return instance;		
	}
	
	 private boolean isFirstLogin = true;
	 private static Date loginTime;
	  
	/**
	 * 
	 */
	private MyTrader(){}
	
	private MyTrader(TraderCallback callback,MarketCallBack marketCallBack) {
		this.callback = callback;
		this.marketCallBack = marketCallBack;
	}

	public void initalize(){
		try
	    {
	      Date now = new Date();
	      int hour = now.getHours();
	      if (this.isFirstLogin)
	      {
	        Thread thread = new Thread(new Runnable()
	        {
	          public void run()
	          {
	      		logger.info("CTP连接请求发送");
	      		logger.info("CTP交易地址："+MyTrader.this.ctpTradeAddress);
	            MyTrader.loginTime = new Date();
	            CThostFtdcTraderApi tapi = CThostFtdcTraderApi.CreateFtdcTraderApi();
	            MyTrader.this.traderSpiImpl = new TraderSpiImpl(tapi);
	            MyTrader.this.traderSpiImpl.setCallback(MyTrader.this.callback);
	            tapi.RegisterSpi(MyTrader.this.traderSpiImpl);
	            tapi.RegisterFront(MyTrader.this.ctpTradeAddress);
	            tapi.SubscribePublicTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
	            tapi.SubscribePrivateTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
	            System.out.println("交易初始化      " + MyTrader.this.ctpTradeAddress);
	            tapi.Init();
	            tapi.Join();
	          }
	        });
	        thread.start();
	        isFirstLogin = false;
	      }

	    }catch (Exception e){
	      logger.info("CTP登陆请求异常");
	    }
	    logger.info("CTP登陆请求end------------------------");
	}
	
	//手动登录
	public void login(){
		traderSpiImpl.loginOut();
		traderSpiImpl.login();
	}
	
	//行情接口
	public void initalizeK(){
		try{
			Thread thread = new Thread(new Runnable(){
			      public void run(){
			    	CThostFtdcMdApi thostapi = CThostFtdcMdApi.CreateFtdcMdApi();
			    	thostSpiImpl = new ThostSpiImpl(thostapi);		//设置回调接受对象
			    	thostSpiImpl.setMarketCallBack(marketCallBack);
			  		thostapi.RegisterSpi(thostSpiImpl);
			  		thostapi.RegisterFront(ctp1_ThostAddress);
			  		System.out.println("行情初始化"+"      "+ctp1_ThostAddress);
			  		thostapi.Init();
			  		thostapi.Join();
			      }
			    });
			    thread.start();
		}catch(Exception e){
			logger.info("CTP行情请求异常");
		}
		    
	}
	
	//测试的交易地址
	private String ctpTradeAddress="tcp://180.168.146.187:10000";
	
	//正式的交易地址
	//private String ctpTradeAddress="tcp://ctp-cnc1.jingduqh.com:41205";

	private TraderSpiImpl traderSpiImpl;
	//获取行情
	private ThostSpiImpl thostSpiImpl;
	
	public TraderSpiImpl getTraderApi() {
		return traderSpiImpl;
	}

	private TraderCallback callback;
	
	private MarketCallBack marketCallBack;
}