package com.xmszit.futures.web.controllers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ResourceServlet;

import com.hsit.common.MD5Util;
import com.hsit.common.uac.entity.User;
import com.xmszit.futures.system.ctp.MyTrader;
import com.xmszit.futures.system.dao.OrderItemDao;
import com.xmszit.futures.system.entity.ClientUser;
import com.xmszit.futures.system.entity.Future;
import com.xmszit.futures.system.entity.OrderItem;
import com.xmszit.futures.system.entity.emun.ClientTypeEnum;
import com.xmszit.futures.system.entity.emun.EveningUpEnum;
import com.xmszit.futures.system.entity.emun.FutureTypeEnum;
import com.xmszit.futures.system.entity.emun.OrderStatus;
import com.xmszit.futures.system.entity.emun.OrderWayEnum;
import com.xmszit.futures.system.entity.query.ClientUserQuery;
import com.xmszit.futures.system.entity.query.FutureQuery;
import com.xmszit.futures.system.entity.query.OrderItemQuery;
import com.xmszit.futures.system.service.ClientUserService;
import com.xmszit.futures.system.service.FutureService;
import com.xmszit.futures.system.service.OrderItemService;
import com.xmszit.futures.web.BaseController;
import com.xmszit.futures.web.models.ListJson;
import com.xmszit.futures.web.utils.OrderNoUtil;
import com.xmszit.futures.web.utils.SinaJsonUtil;
import com.xmszit.jctp.trader.enums.CombOffsetFlag;
import com.xmszit.jctp.trader.enums.Direction;
import com.xmszit.jctp.trader.enums.ForceCloseReason;

@Controller
@RequestMapping("/web/orderControl")
public class OrderController extends BaseController<OrderItem, OrderItemQuery> {
	
	static Logger logger = LoggerFactory.getLogger("business");
	
	@Autowired
	private OrderItemService service;
	
	@Autowired
	private OrderItemDao dao;
	
	@Autowired
	private ClientUserService clientUserService;
	
	@Autowired
	private FutureService futureService;
	
	@Override
	public OrderItemService getService() {
		return service;
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//出入金
	@RequestMapping("EntryExitPayments")
	public String entryExitPayments(HttpSession session){
		ClientUser cu = (ClientUser) session.getAttribute("loginUser");
		if(cu==null){
			return "web/login";
		}
		cu = clientUserService.getById(cu.getId());
		
		int count = 0;
		
		//查看入金记录
		if(cu.getClientType()==ClientTypeEnum.MemberUnit){
			count = jdbcTemplate.queryForInt("select count(*) from exitentry where status='1' and exitOrEntry='1' and phoneNumber=?",new Object[]{cu.getPhoneNumber()});
		}
		session.setAttribute("count", count);
		session.setAttribute("my", cu);
		return "web/EntryExitPayments";
	}
	
	//买入页面
	@RequestMapping("buyOrder/{type}/{code}")
	public String buyOrder(@PathVariable String type,@PathVariable String code,HttpServletRequest req){
		FutureQuery futureQuery = new FutureQuery();
		futureQuery.setFutureCode(code);
		Future future = futureService.getEntity(futureQuery);
		req.setAttribute("orderWay", type);
		req.setAttribute("future", future);
		return "web/buyOrder";
	}
	
	//价格计算
	/**
	 * 
	 * @param fCode	合约代码
	 * @param dang	止损档数
	 * @param shou	手数
	 * @return
	 */
	@RequestMapping(value = "totalMoney",method = RequestMethod.POST)
	@ResponseBody
	public Object totalMoney(String fCode,Integer dang,Integer shou){
		
		FutureQuery fQ = new FutureQuery();
		fQ.setFutureCode(fCode);
		Future f = futureService.getEntity(fQ);

		//最新价
		BigDecimal newPrice = SinaJsonUtil.getNewPrice(fCode);

		String result = f.getTiggerStop();
		
		float stopLossMargin = 0;
		
		try{
			stopLossMargin = (Float.parseFloat(result.split(";")[0]) + Float.parseFloat(result.split(";")[dang-1]))*shou;
		}catch(Exception e){
			logger.error("ddddddddddd止损异常："+result+"exception:"+e.getMessage().toString()+" dang:"+dang);
		}

		//交易手续费
		BigDecimal p = null;
		//信息服务费
		BigDecimal m = null;
		
		//百分比 
		if(f.getType()==1){
			//交易手续费
			p = newPrice.multiply(new BigDecimal(f.getUnit())).multiply(new BigDecimal(f.getExchangeCommission()*f.getMinSandards()));
			//信息服务费
			m = newPrice.multiply(new BigDecimal(f.getUnit() * f.getExchangeMarginRatio()*f.getRateOfInformationService()));
			
			//固定值
		}else if(f.getType()==2){
			//交易手续费
			p = new BigDecimal(f.getExchangeCommission() * f.getMinSandards());
			//信息服务费
			m = newPrice.multiply(new BigDecimal(f.getUnit() * f.getExchangeMarginRatio()*f.getRateOfInformationService()));
		}

		if(m.subtract(new BigDecimal(27)).doubleValue()<0){
			m = new BigDecimal(27);
		}
		
		m = m.multiply(new BigDecimal(shou));
		p = p.multiply(new BigDecimal(shou));

		BigDecimal consolidatedFee = m.add(p);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("exchangePoundageMoney", p);
		map.put("exchangeInfomationMoney", m);			//止损保证金
		map.put("stopLossMargin", stopLossMargin);		//交易综合费
		map.put("consolidatedFee", consolidatedFee);	//冻结保证金
		map.put("total", consolidatedFee.add(new BigDecimal(stopLossMargin)));
		return map;
	}
	
	/**
	 * 止损保证金的计算
	 * @param shou  手数
	 * @param dang	档数
	 */
	public float stopLossMargin(Integer shou,Integer dang,float minChangePrice){
		Integer tmp = 0;	//系数（固定的）
		switch(dang){
			case 1:
				tmp = 3;
				break;
			case 2:
				tmp = 8;
				break;
			case 3:
				tmp = 16;
				break;
			case 4:
				tmp = 30;
				break;
			case 5:
				tmp = 50;
				break;
			case 6:
				tmp = 80;
				break;
		}
		return shou*minChangePrice*tmp;
	}
	
	//生成订单  发送开仓请求
	@RequestMapping("createOrder")
	@ResponseBody
	public synchronized ListJson createOrder(OrderItem orderItem,Integer dang,HttpSession session){
		ListJson LJ = new ListJson();
		
		try{
			//计算所需的止损保证金  信息服务费  交易手续费
			Map<String,Object> map = (Map<String,Object>)(totalMoney(orderItem.getFuturesCode(),dang, orderItem.getCount()));
			
			//止损保证金(冻结)
			float stopLossMargin = (float) (map).get("stopLossMargin");
			
			//交易所信息费
			BigDecimal exchangePoundageMoney = (BigDecimal) (map).get("exchangePoundageMoney");
			
			//信息服务费
			BigDecimal exchangeInfomationMoney = (BigDecimal) (map).get("exchangeInfomationMoney");
			
			orderItem.setExchangePoundageMoney(exchangePoundageMoney);
			orderItem.setExchangeInfomationMoney(exchangeInfomationMoney);
			
			//最新价 	获取实时价
			BigDecimal newPrice = SinaJsonUtil.getNewPrice(orderItem.getFuturesCode());
			
			//止损平仓价
			if(orderItem.getOrderWay()==OrderWayEnum.MORE){
				//买多
				orderItem.setStopLossPrice(newPrice.subtract(orderItem.getStopLoss().multiply(new BigDecimal(orderItem.getCount()))));
				
				if(orderItem.getTargetProfit().multiply(new BigDecimal(1)).doubleValue()>0){
					orderItem.setTargetProfitPrice(newPrice.add(orderItem.getTargetProfit().multiply(new BigDecimal(orderItem.getCount()))));
				}
			}else if(orderItem.getOrderWay()==OrderWayEnum.LESS){
				//买空
				orderItem.setStopLossPrice(newPrice.add(orderItem.getStopLoss().multiply(new BigDecimal(orderItem.getCount()))));
				
				if(orderItem.getTargetProfit().multiply(new BigDecimal(1)).doubleValue()>0){
					orderItem.setTargetProfitPrice(newPrice.subtract(orderItem.getTargetProfit().multiply(new BigDecimal(orderItem.getCount()))));
				}
			}
			
			//总金额（冻结保证金）
			BigDecimal total = (BigDecimal) (map).get("total");
			
			ClientUser clientUser = (ClientUser) session.getAttribute("loginUser");
			
			if(clientUser==null){
				LJ.setMessage("请登录！");
				LJ.setSuccess(false);
				return LJ;
			}
	
			ClientUser cu = clientUserService.getById(clientUser.getId());
			
			//余额
			if(cu.getEnableMoney().subtract(total).floatValue()<0){
				LJ.setMessage("余额不足");
				LJ.setSuccess(false);
				return LJ;
			}
			//账号
			orderItem.setAccount(cu.getPhoneNumber());
			//订单号  生成报单号
			String baodan = OrderNoUtil.getUniqueNumber(jdbcTemplate);
			
			orderItem.setClientOrderNo(baodan);	//开仓报单
			
			orderItem.setCloseOrderNo(""); 	//平仓报单
			orderItem.setCancelOrderNo("");
			
			orderItem.setCreateOrderTime(new Date());
			
			orderItem.setFreezeDepositMoney(new BigDecimal(stopLossMargin));
			
			orderItem.setStatus(OrderStatus.Request); 	//请求状态
			
			orderItem.setPayMoney(total);
			
			orderItem.setExchangeId("");
			orderItem.setOrderSysId("");
			
			//下单价格(冻结保证金)
			orderItem.setUnitPrice(newPrice);
			orderItem.setIsEveningUp(EveningUpEnum.NO);
			//扣除
			cu.setEnableMoney(cu.getEnableMoney().subtract(total));
			
			
			//查找上级会员单位
			ClientUser user = clientUserService.parentMember(cu.getLeaderUserID());
			
			//保证金额度大于10万可交易
			if(user.getBailCash().doubleValue()<1){
				LJ.setMessage("无保证金额度,请联系会员单位");
				LJ.setSuccess(false);
				return LJ;
			}
			
			clientUserService.save(cu);
			
			//警告	冻结保证金>可用保证金
			if(total.doubleValue()>user.getEnableBailCash().doubleValue()){
				orderItem.setWarn(1);	//
			}else{
				orderItem.setWarn(0);	//
			}
			
			//占用保证金 + 止损保证金
			//user.setSeatBailCash(user.getSeatBailCash().add(orderItem.getPayMoney()));
			//user.setSeatBailCash(user.getSeatBailCash().add(orderItem.getFreezeDepositMoney()));
			//额度     - 占用
			//user.setEnableBailCash(user.getBailCash().subtract(user.getSeatBailCash()));
			
			//clientUserService.save(user);
			
			logger.info("开仓：报单号:"+orderItem.getClientOrderNo()+",合约："+orderItem.getFuturesCode()+",手："+orderItem.getCount()+",方向："+orderItem.getOrderWay()+",开仓价："+orderItem.getUnitPrice());
			
			//下单操作
	    	MyTrader.getInstance().getTraderApi().reqOrderInsert(
	    			orderItem.getFuturesCode(), 	//合约代码
	    			orderItem.getClientOrderNo(), 	//订单号
	    			orderItem.getOrderWay()==OrderWayEnum.MORE?Direction.BUY:Direction.SELL, 		//买卖方向;0：买，1：卖
	    			CombOffsetFlag.OPEN, //组合开平标志;0:开仓；1：平仓；2：强平
	    			ForceCloseReason.NOT_FORCE, //强平原因;0:非强平;1：资金不足;2:客户超仓;3:会员超仓;4:持仓非整数倍;5:违规;6:其它;7:自然人临近交割
	    			orderItem.getCount(), 			//count 数量
	    			orderItem.getUnitPrice().doubleValue(), 			//price 价格
	    			orderItem.getStopLossPrice().doubleValue());		//stopPrice 止损价
			
	    	orderItem.setStatus(OrderStatus.Request); 		//下单请求阶段
			
			orderItem.setIsEveningUp(EveningUpEnum.NO);		
			LJ.setMessage("下单申请中！");
			service.save(orderItem);
			LJ.setSuccess(true);
			return LJ;
		}catch(Exception e){
			
		}
		return LJ;
	}
	
	//交易明细页面
	@RequestMapping("myBusiness/{type}")
	public String myBusiness(@PathVariable String type,String fCode,HttpServletRequest request){
		request.setAttribute("type", type);
		if(type.equals("offsetGainLoss")){
			request.setAttribute("title", "持仓");
		}else{
			request.setAttribute("title", "订单");
		}
		request.setAttribute("fCode", fCode);
		return "web/myBusiness";
	}
	
	//成交记录
	@RequestMapping("orderRecord")
	public String orderRecord(){
		return "web/orderRecord";
	}
	
	//交易明细
	@RequestMapping("getMyOrderList")
	@ResponseBody
	public ListJson getMyOrderList(String fCode,String type,HttpSession session){
		ListJson LJ = new ListJson();
		
		try{
			
			ClientUser cu = (ClientUser) session.getAttribute("loginUser");
			if(cu==null){
				LJ.setMessage("login");
				LJ.setSuccess(false);
				return LJ;
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date today = null;
			Date yesterday = null;
			
			if(new Date().getHours()<21){
				today = new Date();
				today.setHours(15);
				today.setMinutes(0);
				today.setSeconds(0);
				
				yesterday = new Date(today.getTime() - 86400000L);
				yesterday.setHours(21);
				yesterday.setMinutes(0);
				yesterday.setSeconds(0);
			}else{
				today = new Date();
				today.setHours(24);
				today.setMinutes(0);
				today.setSeconds(0);
				
				yesterday = new Date();
				yesterday.setHours(21);
				yesterday.setMinutes(0);
				yesterday.setSeconds(0);
			}
			
			
			
			StringBuffer str = new StringBuffer();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("account", cu.getPhoneNumber());
			map.put("isEveningUp", type.equals("FloatingPL")?EveningUpEnum.YES:EveningUpEnum.NO);
			str.append("FROM OrderItem where account=:account and isEveningUp=:isEveningUp");
			if(fCode!=null && !fCode.equals("")){
				str.append(" and futuresCode=:futuresCode");
				map.put("futuresCode", fCode);
			}
			
			if(type.equals("FloatingPL")){
				str.append(" and createOrderTime>:yesterday and createOrderTime<:today");
				map.put("yesterday", yesterday);
				map.put("today", today);
			}
			
			
			List<OrderItem> list = dao.findHql(str.toString(), map);
			
			//成交记录加载  手续费总额,信息费总额。。。
			if(type.equals("FloatingPL")){
				Map<String, Object> totalMap = jdbcTemplate.queryForMap("select sum(offsetGainAndLoss) as offsetGainAndLoss,sum(exchangePoundageMoney) as exchangePoundageMoney,sum(exchangeInfomationMoney) as exchangeInfomationMoney from orderitem where isEveningUp=? and account=? and createOrderTime>? and createOrderTime<?",
								new Object[]{EveningUpEnum.YES.ordinal(),cu.getPhoneNumber(),formatter.format(yesterday),formatter.format(today)});
				LJ.setEntity(totalMap);
			}else{
				//持仓 加载各个 交易单位  手数
				List<Map<String, Object>> totalMapList = jdbcTemplate.queryForList("select futureCode,unit from future");
				LJ.setEntity(totalMapList);
			}
	
			LJ.setRows(list);
			LJ.setSuccess(true);
			return LJ;
		}catch(Exception e){
			
		}
		return LJ;
	}
	
	//加载本期货持仓数
	//交易明细
	@RequestMapping("getMyOrderListCounts")
	@ResponseBody
	public ListJson getMyOrderListCounts(String fCode,HttpSession session){
		ListJson LJ = new ListJson();
		try{
			
			ClientUser cu = (ClientUser) session.getAttribute("loginUser");
			if(cu==null){
				LJ.setMessage("login");
				LJ.setSuccess(false);
				return LJ;
			}
			Map<String, Object> totalMap = jdbcTemplate.queryForMap("select count(*) as total from orderitem where isEveningUp=? and account=? and futuresCode=? ", new Object[]{EveningUpEnum.NO.ordinal(),cu.getPhoneNumber(),fCode});
			LJ.setEntity(totalMap);
			return LJ;
		}catch(Exception E){
			
		}
		return LJ;
	}
	
	//手动平仓
	@RequestMapping("eveningUp")
	@ResponseBody
	public ListJson eveningUp(String oId,HttpSession session){
		ListJson LJ = new ListJson();
		try{
			ClientUser cu = (ClientUser) session.getAttribute("loginUser");
			if(cu==null){
				LJ.setMessage("login");
				LJ.setSuccess(false);
				return LJ;
			}
			return service.eveningUp(oId,false);
		}catch(Exception E){
			
		}
		return LJ;
	}
	
	
	@RequestMapping(value="/businessDetail")
	public String businessDetail(String orderNo,HttpServletRequest request,HttpSession session){
		try{
			ClientUser cu = (ClientUser) session.getAttribute("loginUser");
			if(cu==null){
				return "web/login";
			}
			//订单必须是本人才可查看
			OrderItemQuery itemQuery = new OrderItemQuery();
			itemQuery.setClientOrderNo(orderNo);
			OrderItem OI = service.getEntity(itemQuery);
			if(OI==null){
				return "web/login";
			}
		
			if(!OI.getAccount().equals(cu.getPhoneNumber())){
				return "web/login";
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			request.setAttribute("orderitem", OI);
			request.setAttribute("time", formatter.format(OI.getCreateOrderTime()));
			return "web/businessDetails";
		}catch(Exception E){
			
		}
		
		return "";
	}
	
	@RequestMapping(value="/orderRecordDetails/{id}")
	public String orderRecordDetails(@PathVariable String id,HttpServletRequest request,HttpSession session){
		
		try{
			ClientUser cu = (ClientUser) session.getAttribute("loginUser");
			if(cu==null){
				return "web/login";
			}
			//订单必须是本人才可查看
			OrderItem OI = service.getById(id);
			if(OI==null){
				return "web/login";
			}
			
			if(!OI.getAccount().equals(cu.getPhoneNumber())){
				return "web/login";
			}
			
			request.setAttribute("orderitem", OI);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			request.setAttribute("createOrderTime", formatter.format(OI.getCreateOrderTime()));
			request.setAttribute("closeTime", formatter.format(OI.getCloseTime()));
			
			return "web/orderRecordDetails";
		}catch(Exception E){
			
		}
		return "";
	}
	
	//撤单
	@RequestMapping(value="/cancelReport")
	@ResponseBody
	public ListJson cancelReport(String orderId,HttpSession session){
		
		ListJson LJ = new ListJson();
		
		try{
			
		
			OrderItemQuery OIQ = new OrderItemQuery();
			OIQ.setClientOrderNo(orderId);
			OrderItem OI = service.getEntity(OIQ);
			
			if(OI==null){
				LJ.setSuccess(true);
				LJ.setMessage("单号不存在");
				return LJ;
			}
			
			//交易时间内才可以主动撤单
			FutureQuery futureQuery = new FutureQuery();
			futureQuery.setFutureCode(OI.getFuturesCode());
			Future f = futureService.getEntity(futureQuery);
			
			boolean result = service.beforeEvengUp(f);
			
			if(!result){
				System.out.println("不是交易时间");
				LJ.setMessage("不是交易时间");
				LJ.setSuccess(false);
				return LJ;
			}
			//开仓撤单
			if(OI.getStatus()==OrderStatus.Request || OI.getStatus()==OrderStatus.RequestFinishing){
				OI.setCancelOrderNo(OrderNoUtil.getUniqueNumber(jdbcTemplate));
				MyTrader.getInstance().getTraderApi().reqOrderAction(
						OI.getFuturesCode(), 
						OI.getClientOrderNo(),	//撤单
						OI.getSessionID().toString(),
						OI.getFrontID(), 
						OI.getExchangeId(),
						OI.getOrderSysId().trim(), 
						OI.getUnitPrice().doubleValue(), 
						OI.getCount());
				LJ.setSuccess(true);
				LJ.setMessage("开仓请求撤单");
				return LJ;
			}
			
			if(OI.getStatus()==OrderStatus.closeRequest){
				OI.setCancelOrderNo(OrderNoUtil.getUniqueNumber(jdbcTemplate));
				MyTrader.getInstance().getTraderApi().reqOrderAction(
						OI.getFuturesCode(), 
						OI.getCloseOrderNo(),	//撤单
						OI.getSessionID().toString(),
						OI.getFrontID(), 
						OI.getExchangeId(),
						OI.getOrderSysId().trim(), 
						OI.getUnitPrice().doubleValue(), 
						OI.getCount());
				LJ.setSuccess(true);
				LJ.setMessage("平仓请求撤单");
				return LJ;
			}
			LJ.setSuccess(true);
			LJ.setMessage("该单号不能撤单");
			return LJ;
		
		}catch(Exception E){
			
		}
		return LJ;
	}
}