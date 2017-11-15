package com.xmxnkj.voip.system.ctp;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.xmxnkj.voip.system.entity.ClientUser;
import com.xmxnkj.voip.system.entity.OrderItem;
import com.xmxnkj.voip.system.entity.emun.ClientTypeEnum;
import com.xmxnkj.voip.system.entity.emun.EveningUpEnum;
import com.xmxnkj.voip.system.entity.emun.OrderStatus;
import com.xmxnkj.voip.system.entity.emun.OrderWayEnum;
import com.xmxnkj.voip.system.entity.query.ClientUserQuery;
import com.xmxnkj.voip.system.entity.query.OrderItemQuery;
import com.xmxnkj.voip.system.service.ClientUserService;
import com.xmxnkj.voip.system.service.OrderItemService;
import com.xmxnkj.voip.system.service.TradProcessService;
import com.xmxnkj.voip.system.timer.CDPayLock;
import com.xmxnkj.voip.system.timer.TaskTimer;
import com.xmxnkj.voip.web.utils.SinaJsonUtil;
import com.xmxnkj.jctp.trader.TraderCallback;

@Service
public class TraderCallbackImpl implements TraderCallback{
	
	static Logger logger = LoggerFactory.getLogger("business");
	
	@Autowired
	private OrderItemService orderService;
	
	@Autowired
	private ClientUserService clientUserService;
	
	@Autowired
    @Qualifier("transactionManager")
    protected PlatformTransactionManager txManager;
	
	/**
	 * 报单发送成功
	 */
	@Override
	public void onOrderInserted(final String orderNumber, Integer reqId,
			final Boolean success, final Integer errorId, String errorMsg) {
		try{
				System.out.println("报单发送成功");
				System.out.println("orderNumber:"+orderNumber);
				System.out.println("reqId:"+reqId);
				System.out.println("success:"+success);
				System.out.println("errorId:"+errorId);
				System.out.println("errorMsg:"+errorMsg);
				
				TransactionTemplate tmpl = new TransactionTemplate(txManager);
				tmpl.execute(new TransactionCallbackWithoutResult() {
		            @Override
		            protected void doInTransactionWithoutResult(TransactionStatus status) {
		            	try{
		            		boolean isOpen = true;
		            		
		            		OrderItemQuery itemQuery = new OrderItemQuery();
		            		itemQuery.setClientOrderNo(orderNumber);
		            		OrderItem OI = orderService.getEntity(itemQuery);
		            		
		            		if(OI==null){
		            			itemQuery = new OrderItemQuery();
		            			itemQuery.setCloseOrderNo(orderNumber);
		                		OI = orderService.getEntity(itemQuery);
		                		isOpen = false;
		            		}
		            		
		            		if(OI==null){
		            			return;
		            		}
		            		
		            		if(success){
		            			//下单请求
		            			if(OI.getStatus()==OrderStatus.Request){
		            				//下单部分完成阶段
		            				OI.setStatus(OrderStatus.RequestFinishing);	
		                    		orderService.save(OI);
		            			}
		            			//平仓请求
		            			if(OI.getStatus()==OrderStatus.RequestFinished){
		            				OI.setStatus(OrderStatus.closeFinished);		//平仓完成
		            				OI.setIsEveningUp(EveningUpEnum.YES);
		                    		orderService.save(OI);
		            			}                		
		            		}else{
		            			if(OI!=null){
		                			//查询出用户额度
		                    		ClientUserQuery clientUserQuery = new ClientUserQuery();
		                    		clientUserQuery.setPhoneNumber(OI.getAccount());
		                    		clientUserQuery.setClientType(ClientTypeEnum.Normal);
		                    		ClientUser cu = clientUserService.getEntity(clientUserQuery);
		                    		
		                    		//下单失败
		                    		if(isOpen){
		                    			OI.setStatus(OrderStatus.fail);
		                    			if(errorId==31){
		                    				OI.setFailReason("资金不足");
		                    			}
		                    			
		                    			//缓还
		                    			if(cu.getEnableMoney()==null){
		                    				cu.setEnableMoney(new BigDecimal(0));
		                    			}
		                    			cu.setEnableMoney(cu.getEnableMoney().add(OI.getPayMoney())); 
		                    			clientUserService.save(cu);
		                    			orderService.save(OI);
		                    			
		                    		}else{
		                    			//平仓失败
		                    			OI.setStatus(OrderStatus.closeFail);
		                    			orderService.save(OI);
		                    		}
		                  		}
		            		}
		            		
		            	}catch(Exception e){
		            		e.printStackTrace();
		            		logger.info("报单错误回调异常");
		            	}
		            }
		        });
		}catch(Exception E){
			
		}
	}
	
	/**
	 * 撤单
	 */
	@Override
	public void onOrderCanceled(final String orderNumber, String localId,
			String sysId, Integer notifySequence, String traderId,
			Integer settlementId, String exchangeId) {
		
		logger.info("撤单回调start------------------------");

		try{
				System.out.println("报单被撤单");
				System.out.println("orderNumber:"+orderNumber);
				System.out.println("localId:"+localId);
				System.out.println("sysId:"+sysId);
				System.out.println("notifySequence:"+notifySequence);
				System.out.println("traderId:"+traderId);
				System.out.println("settlementId:"+settlementId);
				System.out.println("exchangeId:"+exchangeId);
				
				System.out.println("开始！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！"+orderNumber);
					
					TransactionTemplate tmpl = new TransactionTemplate(txManager);
					
					tmpl.execute(new TransactionCallbackWithoutResult() {
			            @Override
			            protected void doInTransactionWithoutResult(TransactionStatus status) {
			            		
			            
			            		//判断 是否为开仓
			            		boolean isOpen = true; 
			            		OrderItemQuery itemQuery = new OrderItemQuery();
			            		itemQuery.setClientOrderNo(orderNumber);
			            		OrderItem OI = orderService.getEntity(itemQuery);
			            		
			            		if(OI==null){
			            			itemQuery = new OrderItemQuery();
			            			itemQuery.setCloseOrderNo(orderNumber);
			                		OI = orderService.getEntity(itemQuery);
			                		isOpen = false;
			            		}
			            		
			            		if(OI==null){
			            			return;
			            		}
			            		
			            		//开仓被撤单
			            		if(isOpen){
			                			//开仓撤单
			                    		logger.info("开仓被撤单：报单号:"+OI.getClientOrderNo()+",合约："+OI.getvoipCode()+",手："+OI.getCount()+",方向："+OI.getOrderWay()+",开仓价："+OI.getUnitPrice());
		
			                			clientUserService.cancelOrder(OI);
		
			            		}else{
			            			
			            			if(OI.getStatus()==OrderStatus.closeRequest){
			            				logger.info("平仓被撤单：报单号:"+OI.getCloseOrderNo()+",合约："+OI.getvoipCode()+",手："+OI.getCount()+",方向："+OI.getOrderWay()+",开仓价："+OI.getUnitPrice());
		
			            				//平仓被撤单
			            				//订单状态改变		平仓失败
			            				OI.setStatus(OrderStatus.closeFail);
			            				OI.setCloseDeal(0);
			            				OI.setNotCloseDeal(OI.getCount());
			            				orderService.save(OI);
			        				
			            			}
			            		}
			            	}
					
					});
				
				System.out.println("结束！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！"+orderNumber);
		}catch(Exception E){
			logger.info("CTP撤单回调异常");
		}
		logger.info("撤单回调end------------------------");
	}
	
	/**
	 * 成交回调
	 */
	@Override
	public void onOrderTraded(final String orderNumber, 		//OrderRef
								final String localId, 			//OrderLocalID
								final String sysId,				//OrderSysID
								final Integer notifySequence, 	//NotifySequence
								final String traderId, 			//TraderID
								final Integer settlementId,		//SettlementID
								final String exchangeId, 
								final Integer tradeAmount, 
								final Integer leftAmount,
								final Integer frontID,
								final Integer sessionID) {
		logger.info("交易成功回调start------------------------");
		try{
			
				System.out.println("交易成功或部分交易成功");
				System.out.println("orderNumber:"+orderNumber);
				System.out.println("localId:"+localId);
				System.out.println("sysId:"+sysId);
				System.out.println("notifySequence:"+notifySequence);
				System.out.println("traderId:"+traderId);
				System.out.println("settlementId:"+settlementId);
				System.out.println("exchangeId:"+exchangeId);
				System.out.println("frontID:"+frontID);
				System.out.println("sessionID:"+sessionID);
				System.out.println("tradeAmount:"+tradeAmount);	//已成交(开)  未成交(平)
				System.out.println("leftAmount:"+leftAmount);	//未成交(开)  已成交(平)
				
				TransactionTemplate tmpl = new TransactionTemplate(txManager);
				tmpl.execute(new TransactionCallbackWithoutResult() {
		            @Override
		            protected void doInTransactionWithoutResult(TransactionStatus status) {
		            	try{
		            		//判断 是否为开仓
		            		boolean isOpen = true; 
		            		OrderItemQuery itemQuery = new OrderItemQuery();
		            		itemQuery.setClientOrderNo(orderNumber);	//查开仓订单
		            		OrderItem OI = orderService.getEntity(itemQuery);
		            		
		            		if(OI==null){
		            			itemQuery = new OrderItemQuery();
		            			itemQuery.setCloseOrderNo(orderNumber);	//查平仓订单
		                		OI = orderService.getEntity(itemQuery);
		                		System.out.println("查平仓");
		                		isOpen = false;
		            		}
		            		
		            		if(OI==null){
		            			return;
		            		}
		            		
		            		//开仓操作
		            		if(isOpen){
		            			//判断状态（会重复发送报单结果）
		            			//只有在状态  Request,			//下单请求阶段     RequestFinishing,	//下单部分完成阶段
		            			if(OI.getStatus()==OrderStatus.RequestFinishing  || OI.getStatus()==OrderStatus.Request){
		            				//开多
		                			if(OI.getOrderWay()==OrderWayEnum.MORE){//开多
		                				
		                				if(leftAmount>0){
		                    				OI.setStatus(OrderStatus.RequestFinishing);	//请求继续 部分成交
		                    			}else if(leftAmount==0){
		                    				OI.setStatus(OrderStatus.RequestFinished);	//请求完毕 已成交
		                    			}
		                				
		                			}else if(OI.getOrderWay()==OrderWayEnum.LESS){//开空
		                				//开空
		                				if(leftAmount>0){
		                    				OI.setStatus(OrderStatus.RequestFinishing); //请求继续 部分成交
		                    			}else if(leftAmount==0){
		                    				OI.setStatus(OrderStatus.RequestFinished);  //请求完毕 已成交
		                    				OI.setCreateOrderTime(new Date()); 	//开仓成交时间
		                    			}
		                			}
		                			//leftAmount 未成交手数
		                			OI.setOpenDeal(tradeAmount);
		                			OI.setNotOpenDeal(leftAmount);
		                			OI.setOrderSysId(sysId);
		                			OI.setOrderSysOpenId(sysId);
		                			OI.setExchangeId(exchangeId);
		                			OI.setFrontID(frontID.toString());
		                			OI.setSessionID(sessionID);
		                			OI.setOrderLocalID(localId);
		                			OI.setTraderID(traderId);
		                			orderService.save(OI);
		                    		logger.info("开仓交易成功：报单号:"+OI.getCloseOrderNo()+",合约："+OI.getvoipCode()+",手："+OI.getCount()+",方向："+OI.getOrderWay()+",开仓价："+OI.getUnitPrice()+",已成交："+tradeAmount+",未成交："+leftAmount);
		            			}
		            		}else{
		            		//平仓操作    平仓的(已成交  未成交 是相反的)
		            			//必须是平仓请求状态
		            			if(OI.getStatus()==OrderStatus.closeRequest){
		                			if(leftAmount==0){		                				
		                				if(OI.getStatus()==OrderStatus.closeFinished  && OI.getIsEveningUp()==EveningUpEnum.YES ){
		                					//已经平仓过了
		                				}else{
		                					//平仓后的业务
		                					orderService.eveningUpAfter(OI.getId(), sysId, exchangeId, frontID.toString(), leftAmount, Integer.valueOf(OI.getCount().intValue() - leftAmount.intValue()), sessionID.toString(), localId, traderId, Integer.valueOf(0));
		                    				logger.info("平仓交易成功：报单号:"+OI.getCloseOrderNo()+",合约："+OI.getvoipCode()+",手："+OI.getCount()+",方向："+OI.getOrderWay()+",开仓价："+OI.getUnitPrice()+",已成交："+leftAmount+",未成交："+tradeAmount);
		                				}
		                				
		                			}else if(leftAmount>0){
		                				//平仓还未完成
		                				OI.setStatus(OrderStatus.closeRequest);
		                				OI.setCloseDeal(tradeAmount);
			                			OI.setNotCloseDeal(leftAmount);
		                				orderService.save(OI);
		                			}
		            			
		                			//系统默认平仓（自动平仓后一分钟自动结算）后的业务处理
		            			}else if(OI.getStatus()==OrderStatus.closeFinished){
		            				
		            				//记录假平仓后的 真是实际盈亏
		                			if(OI.getWarn()==2 && OI.getRealOffsetGainAndLoss().doubleValue()==0){
		                				
		                				BigDecimal newPrice = SinaJsonUtil.getNewPrice(OI.getvoipCode());
		                				
		                				if(newPrice.doubleValue()>0){
		                					
		                					//多
		                    				if(OI.getOrderWay()==OrderWayEnum.MORE){
		                    					OI.setRealOffsetGainAndLoss(OI.getUnitPrice().subtract(newPrice).multiply(new BigDecimal(OI.getCount()).multiply(new BigDecimal(TaskTimer.minPriceChangeMap.get(OI.getvoipCode())))));
		                    				}else if(OI.getOrderWay()==OrderWayEnum.LESS){
		                    					OI.setRealOffsetGainAndLoss((newPrice.subtract(OI.getUnitPrice())).multiply(new BigDecimal(OI.getCount()).multiply(new BigDecimal(TaskTimer.minPriceChangeMap.get(OI.getvoipCode())))));
		                    				}
		                    				OI.setCloseDeal(leftAmount);
				                			OI.setNotCloseDeal(tradeAmount);
		                    				orderService.save(OI);
		                				}
		                			}
		            			}
		            		}            		
		            	}catch(Exception e){
		            		e.printStackTrace();
		            		
		            		logger.info("成交回调异常");
		            		
		            	}
		            }
		        });
		}catch(Exception E){
			logger.info("CTP报单成功回调异常");
		}
		logger.info("交易成功回调end------------------------");
	}

	@Override
	public void onLogined(Boolean success, int sessionId, int errorId,
			String errorMsg) {
		System.out.println("success:"+success);
		System.out.println("sessionId:"+sessionId);
		System.out.println("errorId:"+errorId);
		System.out.println("errorMsg:"+errorMsg);
	}

	@Override
	public void OnRspOrderAction() {
		
	}
}