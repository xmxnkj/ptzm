package com.xmxnkj.voip.system.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hsit.common.service.AppBaseServiceImpl;
import com.xmxnkj.voip.system.ctp.MyTrader;
import com.xmxnkj.voip.system.dao.OrderItemDao;
import com.xmxnkj.voip.system.entity.ClientUser;
import com.xmxnkj.voip.system.entity.Future;
import com.xmxnkj.voip.system.entity.OrderItem;
import com.xmxnkj.voip.system.entity.emun.ClientTypeEnum;
import com.xmxnkj.voip.system.entity.emun.EveningUpEnum;
import com.xmxnkj.voip.system.entity.emun.OrderStatus;
import com.xmxnkj.voip.system.entity.emun.OrderWayEnum;
import com.xmxnkj.voip.system.entity.query.ClientUserQuery;
import com.xmxnkj.voip.system.entity.query.FutureQuery;
import com.xmxnkj.voip.system.entity.query.OrderItemQuery;
import com.xmxnkj.voip.system.service.ClientUserService;
import com.xmxnkj.voip.system.service.voipervice;
import com.xmxnkj.voip.system.service.OrderItemService;
import com.xmxnkj.voip.system.timer.TaskTimer;
import com.xmxnkj.voip.web.models.ListJson;
import com.xmxnkj.voip.web.utils.OrderNoUtil;
import com.xmxnkj.voip.web.utils.SinaJsonUtil;
import com.xmxnkj.jctp.trader.enums.CombOffsetFlag;
import com.xmxnkj.jctp.trader.enums.Direction;
import com.xmxnkj.jctp.trader.enums.ForceCloseReason;

@Service
@Transactional
public class OrderItemServiceImpl extends AppBaseServiceImpl<OrderItem, OrderItemQuery> implements OrderItemService{
	
	static Logger logger = LoggerFactory.getLogger("business");
	
	@Autowired
	private ClientUserService clientUserService;
	
	@Autowired
	private voipervice voipervice;
	
	@Autowired
	private OrderItemDao dao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public OrderItemDao getDao() {
		return dao;
	}
	
	//isAutomatic 是否为定时平仓
	@Override
	public ListJson eveningUp(String oId,boolean isAutomatic) {
		ListJson LJ = new ListJson();
		OrderItemQuery itemQuery = null;
		OrderItem item = null;
		
		synchronized (this) {
			itemQuery = new OrderItemQuery();
			itemQuery.setClientOrderNo(oId);
			item = getEntity(itemQuery);
			if(item==null){
				LJ.setMessage("订单异常");
				LJ.setSuccess(false);
				return LJ;
			}
			
			//持仓状态才能平仓
			if(item.getStatus()==OrderStatus.RequestFinished || item.getStatus()==OrderStatus.closeFail){
				
			}else{
				LJ.setMessage("报单状态不是持仓状态,不能平仓");
				LJ.setSuccess(false);
				return LJ;
			}
		}
		
		//不可重复报单
		if(item.getStatus()==OrderStatus.closeRequest){
			System.out.println("已经报过单了");
			LJ.setMessage("已经报过单了");
			LJ.setSuccess(false);
			return LJ;
		}
		
		FutureQuery fQ = new FutureQuery();
		//生成平仓报单
		item.setCloseOrderNo(OrderNoUtil.getUniqueNumber(jdbcTemplate));
		fQ.setFutureCode(item.getvoipCode());
		Future future = voipervice.getEntity(fQ);
		
		if(!isAutomatic){
			
			boolean result = beforeEvengUp(future);
			
			if(!result){
				System.out.println("不是交易时间");
				LJ.setMessage("不是交易时间");
				LJ.setSuccess(false);
				return LJ;
			}
			
		}else{
			item.setWarn(3); 	//自动平仓标识
		}
		
		//日志
		logger.info("平仓：报单号:"+item.toString());

		//平仓
		if(item.getIsEveningUp()==EveningUpEnum.NO){

			//平仓盈亏
			//最新价
			BigDecimal newPrice = SinaJsonUtil.getNewPrice(item.getvoipCode());
			
			if(item.getOrderWay()==OrderWayEnum.MORE){
				//平仓盈亏
				item.setOffsetGainAndLoss(new BigDecimal((newPrice.doubleValue()-item.getUnitPrice().doubleValue())*item.getCount()*future.getUnit()));
			}

			if(item.getOrderWay()==OrderWayEnum.LESS){
				//平仓盈亏	包含了手数
				item.setOffsetGainAndLoss(new BigDecimal((item.getUnitPrice().doubleValue()-newPrice.doubleValue())*item.getCount()*future.getUnit()));
			}
			//平仓价
			item.setEveningUpPrice(newPrice);
			//平仓时间
			item.setCloseTime(new Date());

			//平仓请求中
			item.setStatus(OrderStatus.closeRequest);
			
			save(item);
			
			//平今
		/*	if(item.getCloseType().equals("T")){
				
			}
			if(item.getCloseType().equals("T")){
				
			}*/
			
			//平仓操作指令
	    	MyTrader.getInstance().getTraderApi().reqOrderInsert(
	    			item.getvoipCode(), 		//合约代码
	    			item.getCloseOrderNo(), 	//平仓报单号
	    			item.getOrderWay()==OrderWayEnum.MORE?Direction.SELL:Direction.BUY, 		//买卖方向;0：买，1：卖
	    			CombOffsetFlag.CLOSETODAY, //组合开平标志;0:开仓；1：平仓；2：强平 3：平今 4：平昨
	    			ForceCloseReason.NOT_FORCE, //强平原因;0:非强平;1：资金不足;2:客户超仓;3:会员超仓;4:持仓非整数倍;5:违规;6:其它;7:自然人临近交割
	    			item.getCount(), 			//count 数量
	    			//item.getUnitPrice().doubleValue(), 			//price 价格
	    			TaskTimer.codeNewPrice.get(item.getvoipCode()),
	    			item.getStopLossPrice().doubleValue());		//stopPrice 止损价
	    	
			LJ.setSuccess(true);
			return LJ;
	}
		return null;
}
	@Override
	public boolean beforeEvengUp(Future future){

		FutureQuery fq = new FutureQuery();
		fq.setFutureCode(future.getFutureCode());
		
		String startTime1 = future.getStartTime1();
		String endTime1 = future.getEndTime1();
		
		String startTime2 = future.getStartTime2();
		String endTime2 = future.getEndTime2();
		
		String startTime3 = future.getStartTime3();
		String endTime3 = future.getEndTime3();
		
		String startTime4 = future.getStartTime4();
		String endTime4 = future.getEndTime4();
		
		Date start = new Date();
		start.setHours(Integer.parseInt(startTime1.split(":")[0]));
		start.setMinutes(Integer.parseInt(startTime1.split(":")[1]));
		start.setSeconds(Integer.parseInt(startTime1.split(":")[2]));
		Date end = new Date();
		end.setHours(Integer.parseInt(endTime1.split(":")[0]));
		end.setMinutes(Integer.parseInt(endTime1.split(":")[1]));
		end.setSeconds(Integer.parseInt(endTime1.split(":")[2]));
		
		Date now = new Date();
		
		if(start.getTime()<now.getTime() && now.getTime()<end.getTime()){
			return true;
		}
		
		start.setHours(Integer.parseInt(startTime2.split(":")[0]));
		start.setMinutes(Integer.parseInt(startTime2.split(":")[1]));
		start.setSeconds(Integer.parseInt(startTime2.split(":")[2]));
		end.setHours(Integer.parseInt(endTime2.split(":")[0]));
		end.setMinutes(Integer.parseInt(endTime2.split(":")[1]));
		end.setSeconds(Integer.parseInt(endTime2.split(":")[2]));
		
		if(start.getTime()<now.getTime() && now.getTime()<end.getTime()){
			return true;
		}
		
		start.setHours(Integer.parseInt(startTime3.split(":")[0]));
		start.setMinutes(Integer.parseInt(startTime3.split(":")[1]));
		start.setSeconds(Integer.parseInt(startTime3.split(":")[2]));
		end.setHours(Integer.parseInt(endTime3.split(":")[0]));
		end.setMinutes(Integer.parseInt(endTime3.split(":")[1]));
		end.setSeconds(Integer.parseInt(endTime3.split(":")[2]));
		
		if(start.getTime()<now.getTime() && now.getTime()<end.getTime()){
			return true;
		}
		
		if(startTime4!=null && !startTime4.equals("") && endTime4!=null && !endTime4.equals("")){
			start.setHours(Integer.parseInt(startTime4.split(":")[0]));
			start.setMinutes(Integer.parseInt(startTime4.split(":")[1]));
			start.setSeconds(Integer.parseInt(startTime4.split(":")[2]));
			end.setHours(Integer.parseInt(endTime4.split(":")[0]));
			end.setMinutes(Integer.parseInt(endTime4.split(":")[1]));
			end.setSeconds(Integer.parseInt(endTime4.split(":")[2]));
			
			if(start.getTime()<now.getTime() && now.getTime()<end.getTime()){
				return true;
			}
		}
		
		return false;
	}
	
	//平仓后续处理
	@Override
	public synchronized void eveningUpAfter(String orderID,String sysId, String exchangeId,
			String frontID, Integer closeDeal, Integer notCloseDeal,
			String sessionID, String localId, String traderId,Integer status) {
		
		OrderItem OI = getById(orderID);
		
		if(OI==null){
			return;
		}
		
		//必须是平仓请求状态
		if(OI.getStatus()!=OrderStatus.closeRequest){
			return;
		}
		
		OI.setStatus(OrderStatus.closeFinished);
		OI.setIsEveningUp(EveningUpEnum.YES);
		OI.setCloseTime(new Date()); 	//平仓成交时间
		//平仓完成后结算
		ClientUserQuery clientUserQuery = new ClientUserQuery();
		clientUserQuery.setPhoneNumber(OI.getAccount());
		clientUserQuery.setClientType(ClientTypeEnum.Normal);
		ClientUser cu = clientUserService.getEntity(clientUserQuery);
		//返回钱  冻结保证金 + 盈利
		ClientUser clientUser = clientUserService.getById(cu.getId());
		clientUser.setEnableMoney(clientUser.getEnableMoney().add(OI.getFreezeDepositMoney().add(OI.getOffsetGainAndLoss())));
		clientUserService.save(clientUser);
		
		if(status==2){
			OI.setWarn(status);
		}
		if(!sysId.equals("")){
			OI.setOrderSysId(sysId);
		}
		if(!exchangeId.equals("")){
			OI.setExchangeId(exchangeId);
		}
		OI.setFrontID(frontID.toString());
		OI.setCloseDeal(closeDeal);
		OI.setNotCloseDeal(notCloseDeal);
		OI.setSessionID(Integer.parseInt(sessionID));
		OI.setOrderLocalID(localId);
		OI.setTraderID(traderId);
		save(OI);
	}
	
	@Override
	public void killOrder(String orderID,String orderNo) {
		OrderItem OI = getById(orderID);
		if(OI!=null){
			if(OI.getStatus()==OrderStatus.Request || OI.getStatus()==OrderStatus.RequestFinishing){
				OI.setCancelOrderNo(OrderNoUtil.getUniqueNumber(jdbcTemplate));
				logger.info("自动撤单：报单号:"+OI.getClientOrderNo()+",合约:"+OI.getvoipCode()+"手："+OI.getCount());
				MyTrader.getInstance().getTraderApi().reqOrderAction(
						OI.getvoipCode(), 
						OI.getClientOrderNo(),	//撤单
						OI.getSessionID().toString(),
						OI.getFrontID(), 
						OI.getExchangeId(),
						OI.getOrderSysId().trim(), 
						OI.getUnitPrice().doubleValue(), 
						OI.getCount());
			}
		}
	}
}