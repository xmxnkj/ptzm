package com.xmxnkj.voip.system.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmxnkj.voip.system.ctp.MyTrader;
import com.xmxnkj.voip.system.entity.ClientUser;
import com.xmxnkj.voip.system.entity.Future;
import com.xmxnkj.voip.system.entity.MarketData;
import com.xmxnkj.voip.system.entity.OrderItem;
import com.xmxnkj.voip.system.entity.emun.ClientTypeEnum;
import com.xmxnkj.voip.system.entity.emun.EveningUpEnum;
import com.xmxnkj.voip.system.entity.emun.FutureTypeEnum;
import com.xmxnkj.voip.system.entity.emun.OrderStatus;
import com.xmxnkj.voip.system.entity.emun.OrderWayEnum;
import com.xmxnkj.voip.system.entity.query.ClientUserQuery;
import com.xmxnkj.voip.system.entity.query.FutureQuery;
import com.xmxnkj.voip.system.entity.query.OrderItemQuery;
import com.xmxnkj.voip.system.service.ClientUserService;
import com.xmxnkj.voip.system.service.ExitEntryService;
import com.xmxnkj.voip.system.service.voipervice;
import com.xmxnkj.voip.system.service.MarketDataService;
import com.xmxnkj.voip.system.service.OrderItemService;
import com.xmxnkj.voip.web.utils.SinaJsonUtil;
import com.xmxnkj.jctp.trader.MarketCallBack;
import com.xmxnkj.jctp.trader.TraderCallback;

@Component
public class TaskTimer{
	
	static{
		try{
		
	    	System.out.println(System.getProperty("java.library.path"));
	        System.loadLibrary("thosttraderapi");
	        System.loadLibrary("thosttraderapi_wrap");
	        System.loadLibrary("thostmduserapi");
			System.loadLibrary("thostmduserapi_wrap");
		
		}catch(Exception e){
			
		}
    }      
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	
	static SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
	
	static Logger logger = LoggerFactory.getLogger("business");
	
	//存放实时期货数据
	public static Map<String,TreeMap<String,Object>> codeMap = new HashMap<String,TreeMap<String,Object>>();
	
	
	//最小变动价位
	public static Map<String,Double> minPriceChangeMap = new HashMap<String,Double>();
	
	//最新价
	public static Map<String,Double> codeNewPrice = new HashMap<String,Double>();

	//存放期货自动平仓时间
	public static Set<String> set = new HashSet<String>();
	
	//自动平仓后一分钟
	public static Map<String,String> setAfterMinute = new HashMap<String,String>();
	
	//开始交易时间
	public static Set<String> openSet = new HashSet<String>();
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private voipervice voipervice;
	
	@Resource
	private OrderItemService orderItemService;
	
	@Resource
	private MarketDataService marketDataService;
	
	@Resource
	private ClientUserService clientUserService;
	
	@Resource
	private ExitEntryService exitEntryService;
	
	@Autowired
    @Qualifier("transactionManager")
    protected PlatformTransactionManager txManager;
	
	@PostConstruct
	public void start() throws Exception{
		try{
			TransactionTemplate tmpl = new TransactionTemplate(txManager);
	        tmpl.execute(new TransactionCallbackWithoutResult() {
	            @Override
	            protected void doInTransactionWithoutResult(TransactionStatus status) {
	            	try{
	            		init();
	            	}catch(Exception e){
	            		e.printStackTrace();
	            	}
	            }
	        });

		}catch(Exception E){
			
		}
	}
	
	@Scheduled(fixedRate = 10000)
	public void flushFuture() throws Exception{
		try{
			TransactionTemplate tmpl = new TransactionTemplate(txManager);
	        tmpl.execute(new TransactionCallbackWithoutResult() {
	            @Override
	            protected void doInTransactionWithoutResult(TransactionStatus status) {
	            	try{
	            		init();
	            	}catch(Exception e){
	            		e.printStackTrace();
	            	}
	            }
	        });
		}catch(Exception E){
			
		}
	}
	
	//获取期货代码
	public void init(){
		try{
			List<Future> list = voipervice.getEntities(new FutureQuery());
			for(Future future:list){
				if(!codeMap.containsKey(future.getFutureCode())){
					codeMap.put(future.getFutureCode(), new TreeMap<String,Object>());
				}
				codeNewPrice.put(future.getFutureCode(), 0.0);
				minPriceChangeMap.put(future.getFutureCode(), future.getUnit().doubleValue());
				set.add(future.getStopOutTimeOne());
				set.add(future.getStopOutTimeTwo());	//自动平仓时间
				
				//加工
				String[] str = future.getStopOutTimeOne().split(":");
				
				setAfterMinute.put(str[0]+":"+str[1].substring(0, str[1].length()-1)+"1:"+str[2], future.getStopOutTimeOne());
				
				str = future.getStopOutTimeTwo().split(":");
				
				setAfterMinute.put(str[0]+":"+str[1].substring(0, str[1].length()-1)+"1:"+str[2], future.getStopOutTimeTwo());

				
				openSet.add(future.getStartTime1());
				openSet.add(future.getStartTime2());
				openSet.add(future.getStartTime3());
				if(future.getStartTime4()!=null && future.getStartTime4().equals("")){
					openSet.add(future.getStartTime4());
				}
			}
		}catch(Exception E){
			
		}
	}

		//平仓 1秒查一次
		@Scheduled(fixedRate = 1000)
		public void evengUp(){
			try{
				TransactionTemplate tmpl = new TransactionTemplate(txManager);
		        tmpl.execute(new TransactionCallbackWithoutResult() {
		            @Override
		            protected void doInTransactionWithoutResult(TransactionStatus status) {
		            	try{
		            		selfTrimming();
		            	}catch(Exception e){
		            		e.printStackTrace();
		            	}
		            }
		        });
			}catch(Exception E){
			
			}
		}
	
	
	public boolean flag = true;
	public String time = "";
	
	//判断平仓时间(自动平仓)
	public void selfTrimming(){
		try{
			String now = sdf1.format(new Date().getTime());	//即时时间
			now = now.substring(0,now.length()-2)+"00";
			if(set.contains(now)){
				if(time.equals("")){
					if(flag){
						time = now;
						flag = false;
						logger.info("触发自动平仓");
						//平仓操作
							//需要平仓的期货代码	开仓成交  平仓失败被撤回 的进行平仓
						//挂单状态的单子要撤掉
						List<Map<String, Object>> list = jdbcTemplate.queryForList("select o.ID,o.clientOrderNo,o.status from orderitem o LEFT JOIN future f ON f.futureCode = o.voipCode WHERE o.isEveningUp='0' and stopOutTimeOne='"+now+"' or stopOutTimeTwo='"+now+"' and status='2' or status='6' or status='1'");
						for(Map<String, Object> map:list){
							//开仓撤单指令发送
							if(map.get("status").toString().equals("1")){
								logger.info("自动撤单：报单号:"+map.get("clientOrderNo").toString());
								orderItemService.killOrder(map.get("ID").toString(), map.get("clientOrderNo").toString());
							}else if(map.get("status").toString().equals("2") || map.get("status").toString().equals("6")){
								//平仓操作
								logger.info("自动平仓：报单号:"+map.get("clientOrderNo").toString());
								orderItemService.eveningUp(map.get("clientOrderNo").toString(),true);
							}
						}
						//挂单撤单
					}
				}
				
			}else{
				time = "";
				flag = true;
			}
			
			//每晚9点清分时数据
			if(now.equals("09:00:00")){
				//删除前一天的实时数据
				jdbcTemplate.execute("delete from marketdata where 1=1");
			}
			
			if(now.equals("20:00:00")){
				//把挂的单改为平昨
				jdbcTemplate.execute("update orderitem set closeType='Y'");
			}
			
		}catch(Exception E){
			
		}
}
	
	public static Date dateStyle(String ymd,String date){
		try {
			return  sdf.parse(ymd+" "+date.substring(0,2)+":"
								+date.substring(2,4)+":"
								+date.substring(4,6));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//1秒刷一次 获取期货最新价
	@Scheduled(fixedRate = 1000)
	public void flushZero(){
		try{
			for(String code:codeNewPrice.keySet()){
				codeNewPrice.put(code, SinaJsonUtil.getNewPrice(code).doubleValue());
			}
		}catch(Exception E){
		
		}
	}
	
	//5秒刷一次 判断是否为 止损/止盈 平仓
	@Scheduled(fixedRate = 2000)
	public void stopLossClose(){
		try{
				TransactionTemplate tmpl = new TransactionTemplate(txManager);
		        tmpl.execute(new TransactionCallbackWithoutResult() {
		            @Override
		            protected void doInTransactionWithoutResult(TransactionStatus status) {
		            	try{
		            		//未平仓单
		            		OrderItemQuery itemQuery = new OrderItemQuery();
		            		itemQuery.setIsEveningUp(EveningUpEnum.NO);
		            		List<OrderItem> list = orderItemService.getEntities(itemQuery);
		            		for(OrderItem OI:list){
		            			//下单价
		            			double old = OI.getUnitPrice().doubleValue();
		            			//最新价
		            			double news = codeNewPrice.get(OI.getvoipCode()).doubleValue();
		            			
		            			if(news==0){
		            				continue;
		            			}
		            			
		            			//止损金额
		            			double stoploss = OI.getStopLoss().doubleValue();
		            			//止盈
		            			double stopProfit = OI.getTargetProfit().doubleValue();
		
		            			//多
		            			if(OI.getOrderWay()==OrderWayEnum.MORE){
		            				
		            				//止损	（单手价格）
		            				if(((old-news)*minPriceChangeMap.get(OI.getvoipCode())) >= stoploss){
		            					
		            					
		            					
		            					//平仓
		            					if(OI.getStatus()==OrderStatus.RequestFinished || OI.getStatus()==OrderStatus.closeFail){
		            						
		            						logger.info("止损：报单号:"+OI.getCloseOrderNo()+
		                        					",合约："+OI.getvoipCode()+
		                        					",手："+OI.getCount()+
		                        					",方向："+OI.getOrderWay()+
		                        					",开仓价："+OI.getUnitPrice()+
		                        					",现价："+news+
		                        					",持仓盈亏："+(old-news)*minPriceChangeMap.get(OI.getvoipCode())+
		                        					",方向："+OI.getOrderWay()+
		                        					",止损："+stoploss);
		            						
		            						orderItemService.eveningUp(OI.getClientOrderNo(),false);
		            					}
		            					continue;
		            				}
		            				
		            				//止盈 （单手）
		            				if(stopProfit>0 && (((news-old)*minPriceChangeMap.get(OI.getvoipCode())) >= stopProfit)){
		
		            					//平仓
		            					if(OI.getStatus()==OrderStatus.RequestFinished || OI.getStatus()==OrderStatus.closeFail){
		            						
		            						logger.info("止盈：报单号:"+OI.getCloseOrderNo()+
		                        					",合约："+OI.getvoipCode()+
		                        					",手："+OI.getCount()+
		                        					",方向："+OI.getOrderWay()+
		                        					",开仓价："+OI.getUnitPrice()+
		                        					",现价："+news+
		                        					",持仓盈亏："+(news-old)*minPriceChangeMap.get(OI.getvoipCode())+
		                        					",方向："+OI.getOrderWay()+
		                        					",止盈："+stopProfit);
		            						
		            						orderItemService.eveningUp(OI.getClientOrderNo(),false);
		            					}
		            					continue;
		            					
		            				}
		            				
		            			}else if(OI.getOrderWay()==OrderWayEnum.LESS){
		            				
		            				//止损（单手）
		            				if(((news-old)*minPriceChangeMap.get(OI.getvoipCode())) >= stoploss){
		
		            					//平仓
		            					if(OI.getStatus()==OrderStatus.RequestFinished || OI.getStatus()==OrderStatus.closeFail){
		            						
		            						logger.info("止损：报单号:"+OI.getCloseOrderNo()+
		                        					",合约："+OI.getvoipCode()+
		                        					",手："+OI.getCount()+
		                        					",方向："+OI.getOrderWay()+
		                        					",开仓价："+OI.getUnitPrice()+
		                        					",现价："+news+
		                        					",持仓盈亏："+(old-news)*minPriceChangeMap.get(OI.getvoipCode())+
		                        					",方向："+OI.getOrderWay()+
		                        					",止损："+stoploss);
		            						
		            						orderItemService.eveningUp(OI.getClientOrderNo(),false);
		            					}
		            					continue;
		            				}
		            				
		            				//止盈
		            				if(stopProfit>0 && (((old-news)*minPriceChangeMap.get(OI.getvoipCode())) >= stopProfit)){
		            					
		            					//平仓
		            					if(OI.getStatus()==OrderStatus.RequestFinished || OI.getStatus()==OrderStatus.closeFail){
		            						
		            						logger.info("止盈：报单号:"+OI.getCloseOrderNo()+
		                        					",合约："+OI.getvoipCode()+
		                        					",手："+OI.getCount()+
		                        					",方向："+OI.getOrderWay()+
		                        					",开仓价："+OI.getUnitPrice()+
		                        					",现价："+news+
		                        					",持仓盈亏："+(news-old)*minPriceChangeMap.get(OI.getvoipCode())+
		                        					",方向："+OI.getOrderWay()+
		                        					",止盈："+stopProfit);
		            						
		            						orderItemService.eveningUp(OI.getClientOrderNo(),false);
		            					}
		            					continue;
		            				}
		            				
		            			}
		            		}
		            	}catch(Exception e){
		            		e.printStackTrace();
		            	}
		            }
		        });
		}catch(Exception E){
			
		}
	}
	
	//登陆状态容器
	public final static Map<String,Map<String,Object>> LoginMap = new HashMap<String,Map<String,Object>>();
	
	//登录状态 60秒查一次
	@Scheduled(fixedRate = 60000)
	public void loginStatus(){
		Iterator<String> iterator = LoginMap.keySet().iterator();
		while(iterator.hasNext()){
			String it = iterator.next();
			//登陆状态失效
			try{
				if(((HttpSession)LoginMap.get(it).get("session")).getAttribute("loginUser")==null){
					LoginMap.remove(it);
				}
			}catch(Exception e){
				LoginMap.remove(it);
			}
		}
	}
	
	@Autowired
	private TraderCallback traderCallback;
	
	@Autowired
	private MarketCallBack marketCallBack;
	
	private boolean isIni = true;
	
	//开机登陆一次
	@PostConstruct
	public void ctpLogin(){
		try{
			if(isIni){
				logger.info("启动服务时登陆");
				MyTrader.createInstance(traderCallback,marketCallBack);
				isIni = false;
			}
		}catch(Exception e){
		}
	}
	
	boolean eableLogin = true;
	String date = "";
	//每晚20:50登录
	//String[] startTime = {};
	public static Set<String> startTime = new HashSet<String>();
	
	static{
		startTime.add("21:00:00");
		startTime.add("09:00:00");
		startTime.add("13:30:00");
	}
	
	//交易前登陆
	@Scheduled(fixedRate = 1000)
	public void ctpLoginByTime(){
		try{
			String now = sdf1.format(new Date().getTime());	//即时时间
			now = now.substring(0,now.length()-2)+"00";
			
			if(startTime.contains(now)){
				if(date.equals("")){
					if(eableLogin){
						date = now;
						eableLogin = false;
						System.out.println("-----------------------------------------------");
						//进行登录操作
						MyTrader.createInstance(traderCallback,marketCallBack);
					}
				}
			}else{
				date = "";
				eableLogin = true;
			}
		}catch(Exception e){
		}
	}
	
	//获取实时数据
	@Scheduled(fixedRate = 5000)
	public void flushCTP(){
		try{
				TransactionTemplate tmpl = new TransactionTemplate(txManager);
		        tmpl.execute(new TransactionCallbackWithoutResult() {
		            @Override
		            protected void doInTransactionWithoutResult(TransactionStatus status) {
		            	
		            	TreeMap<String,Object> tmp = new TreeMap<String,Object>();
		        		for(String code:codeMap.keySet()){
		        			String re = SinaJsonUtil.loadJson(FutureTypeEnum.ACTUAL,code);
		        			re = re.substring(re.indexOf("\"")+1, re.length()-2);
		        			String[] str = re.split(",");
		        			String time = str[17]+" "+str[1].substring(0,2)+":"+str[1].substring(2,4)+":"+str[1].substring(4,6);
		        			String price = str[8];
		        			MarketData md = new MarketData();
		    				md.setCode(code);
		    				md.setTime(time);
		    				md.setNewPrice(Double.parseDouble(price));
		    				
		    				tmp = codeMap.get(code);
		    				
		        			if(tmp.containsKey(time)){
		        				
		        			}else{
		        				
		        				marketDataService.save(md);
		        				//插入数据库
		        				tmp.put(time, md);
		        				
		        				if(tmp.size()>10){
		        					tmp.remove(tmp.firstKey());
		        				}
		        				codeMap.put(code, tmp);
		        			}
		        		}
		            }
		        });
		        
		}catch(Exception e){
		}
	}
	
	//清除无用报单
	@Scheduled(fixedRate = 20000)
	public void cancelOrderTimer() throws Exception{
		TransactionTemplate tmpl = new TransactionTemplate(this.txManager);
		tmpl.execute(new TransactionCallbackWithoutResult(){
			protected void doInTransactionWithoutResult(TransactionStatus status){
				try{
					List<Map<String, Object>> list = TaskTimer.this.jdbcTemplate.queryForList("select ID,createOrderTime from orderitem where orderSysId='' and exchangeId='' and isEveningUp='0'");
					for (Map<String, Object> map : list) {
						if (new Date().getTime() - TaskTimer.sdf.parse(map.get("createOrderTime").toString()).getTime() > 10000L) {
							TaskTimer.this.clientUserService.cancelOrder((OrderItem)TaskTimer.this.orderItemService.getById(map.get("ID").toString()));
						}
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
   }
		boolean isClosed = true;
		String tt = "";
	//平仓时间之后的一分钟  处于平仓挂单状态的  结算
	@Scheduled(fixedRate = 5000)
	public void pingCang(){

		 TransactionTemplate tmpl = new TransactionTemplate(this.txManager);
		    tmpl.execute(new TransactionCallbackWithoutResult(){
		      protected void doInTransactionWithoutResult(TransactionStatus status){
		    	  
		        try{
		        	String now = sdf1.format(new Date().getTime());	//即时时间
					now = now.substring(0,now.length()-2)+"00";
					
		        	if(setAfterMinute.containsKey(now)){
		        		
		        		if(tt.equals("")){
		        			
			        		if(isClosed){
			        			isClosed = false;
			        			tt = now;
				        		List<Map<String, Object>> list = TaskTimer.this.jdbcTemplate.queryForList("select o.ID,o.closeTime,o.ID,o.clientOrderNo from orderitem o LEFT JOIN future f ON f.futureCode = o.voipCode WHERE o.isEveningUp='0' and status='3' and stopOutTimeOne='"+setAfterMinute.get(now)+"' or stopOutTimeTwo='"+setAfterMinute.get(now)+"'");
						        for (Map<String, Object> map : list){
						        	long l = TaskTimer.sdf.parse(map.get("closeTime").toString()).getTime();
						           // if (new Date().getTime() - l > 60000L) {
						              orderItemService.eveningUpAfter(map.get("ID").toString(), "", "", "", Integer.valueOf(0), Integer.valueOf(0), "0", "", "", Integer.valueOf(2));
						          //  }
						        }
			        		}
		        		}
		        	}else{
		        		isClosed = true;
	        			tt = "";
		        	}
			          
		        }catch (Exception e){
		          e.printStackTrace();
		        }
		        
		      }
		    });
	}
	
	//刷新占用保证金
	@Scheduled(fixedRate = 5000)
	public void seatBail(){
		try{
		 	TransactionTemplate tmpl = new TransactionTemplate(this.txManager);
		    tmpl.execute(new TransactionCallbackWithoutResult(){
		      protected void doInTransactionWithoutResult(TransactionStatus status){
		        try{
		        	ClientUserQuery clientUserQuery = new ClientUserQuery();
		        	clientUserQuery.setClientType(ClientTypeEnum.MemberUnit);
		        	List<ClientUser> list = clientUserService.getEntities(clientUserQuery);
		        	for(ClientUser clientUser:list){		        		
		        		clientUserService.getSeatBail(clientUser.getId());
		        	}
		        }catch (Exception e){
		          e.printStackTrace();
		        }
		      }
		    });
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	//自动查询支付订单并结算 15分钟1次
	//@Scheduled(fixedRate = 900000)
	public void queryPayOrder(){
		TransactionTemplate tmpl = new TransactionTemplate(this.txManager);
		
	    tmpl.execute(new TransactionCallbackWithoutResult(){
	    	protected void doInTransactionWithoutResult(TransactionStatus status){
	    		String sql = "select * from exitentry where exitOrEntry=0 and entryTime>?";
	    		List<Map<String, Object>>  list = jdbcTemplate.queryForList(sql, new Object[]{sdf2.format(new Date())});
	    		String orderNumber = "";
	    		for(Map<String, Object> map:list){
	    			orderNumber = map.get("payOrderNumber").toString();
	    			String result = SinaJsonUtil.queryPayOrder(orderNumber);
	    			if(StringUtils.isNotEmpty(result)){
	    				JSONObject jsonObject = JSON.parseObject(result.toString());
	    				if(jsonObject.containsKey("status") && jsonObject.get("status").toString().equals("00")){
	    					//进行结算
	    					System.out.println("结算订单："+orderNumber);
	    					try {
								exitEntryService.EntryMoney(map.get("userId").toString(), exitEntryService.getById(map.get("id").toString()));
							} catch (Exception e) {
								e.printStackTrace();
							}
	    				}
	    			}
	    		}
	      }
	     });
	}
}