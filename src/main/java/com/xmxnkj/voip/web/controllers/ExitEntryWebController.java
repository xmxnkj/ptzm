package com.xmszit.futures.web.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hsit.common.MD5Util;
import com.xmszit.futures.system.entity.ClientUser;
import com.xmszit.futures.system.entity.ExitEntry;
import com.xmszit.futures.system.entity.emun.ClientTypeEnum;
import com.xmszit.futures.system.entity.emun.EntryStatusEnum;
import com.xmszit.futures.system.entity.emun.ExitOrEntryEnum;
import com.xmszit.futures.system.entity.query.ExitEntryQuery;
import com.xmszit.futures.system.service.ClientUserService;
import com.xmszit.futures.system.service.ExitEntryService;
import com.xmszit.futures.web.BaseController;
import com.xmszit.futures.web.models.ListJson;
import com.xmszit.futures.web.utils.HttpClientUtils;
import com.xmszit.futures.web.utils.SignKit;
import com.xmszit.futures.web.utils.SpringBeanUtil;
import com.xmszit.yeepay.YeepayService;

@Controller
@RequestMapping("/web/exitEntry")
public class ExitEntryWebController extends BaseController<ExitEntry, ExitEntryQuery> {
	
	public static String payUrl = "http://120.55.16.195/weixin/api.do";//支付
	//public static String payResultUrl = "http://120.42.90.220:8006/futures/web/exitEntry/payResult";	//回调
	public static String p1_MerchantNo = "PC00000117Y";				//商户编号
	public static String _key = "bf589a7aecd5fb3f2fc7bb7788a0523b";	//密钥
	
//	public static String p1_MerchantNo = "10015674212";				//商城版商户编号
//	public static String _key = "mf945Ybf01mz174K53Zo201H574oJgP7H2T6Yp9JsdVxoS9a8QH3Ht3i6V6S";	//商城版密钥 
	
//	public static String settle = "http://120.55.16.195/settle/systemSettle.do";	//代付
	//public static String retUrl = "http://120.42.90.220:8006/futures/web/exitEntry/settleResult";//代付回调
	
//	public static String retUrl = "https://www.yhlhqh.com/web/exitEntry/settleResult";//代付回调
//	public static String payResultUrl = "https://www.yhlhqh.com/web/exitEntry/payResult";	//回调
	@Autowired
	private ExitEntryService service;
	
	@Autowired
	private ClientUserService clientUserService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public ExitEntryService getService() {
		return service;
	}
	
	
	//入金
	/**
	 * 易宝入金
	 * @param enntryri
	 * @param request
	 * @param response
	 * @return
	 * @author fxc
	 */
	@RequestMapping("beforePay")
	public String pay(double enntryri,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		ListJson LJ = new ListJson();
		
		ClientUser cu = (ClientUser) request.getSession().getAttribute("loginUser");
		
		if(cu==null){
			LJ.setMessage("请登录");
			LJ.setSuccess(false);
//			return LJ;
		}
		cu = clientUserService.getById(cu.getId());
		String orderNo = (System.currentTimeMillis()+""+(new Random().nextInt(100)+1));
		
//		//普通用户
//		if(cu.getClientType()==ClientTypeEnum.Normal){
//			//生成订单
//			ExitEntry e = new ExitEntry();
//			e.setPhoneNumber(cu.getPhoneNumber());
//			e.setUserId(cu.getId());
//			e.setEntryTime(new Date());
//			e.setEntryMoney(new BigDecimal(enntryri));
//			e.setExitOrEntry(ExitOrEntryEnum.EntryApply);
//			e.setStatus(EntryStatusEnum.Normal);
//			e.setPayOrderNumber(orderNo);
//			e.setResult(0); //订单初始化
//			service.save(e);
//			e.setExitOrEntry(ExitOrEntryEnum.EntryPass);
//			service.EntryMoney(e.getUserId(), e);
//			
//		}else if(cu.getClientType()==ClientTypeEnum.MemberUnit){
//			
//			//会员单位
//			ExitEntry e = new ExitEntry();
//			e.setPhoneNumber(cu.getPhoneNumber());
//			e.setUserId(cu.getId());
//			e.setEntryTime(new Date());
//			e.setEntryMoney(new BigDecimal(enntryri));
//			e.setExitOrEntry(ExitOrEntryEnum.EntryApply);
//			e.setStatus(EntryStatusEnum.Memunit);
//			e.setPayOrderNumber(orderNo);
//			service.save(e);
//			
//			e.setExitOrEntry(ExitOrEntryEnum.EntryPass);
//			service.EntryMoney(e.getUserId(), e);
//		}
		request.setAttribute("orderNo", orderNo);
		request.setAttribute("money", new BigDecimal(enntryri));
		request.setAttribute("userId", cu.getId());
		return "/web/pay";
//		return send(request,response,orderNo,new BigDecimal(enntryri));
	}
		
	@RequestMapping("ebhuidiao")
	public String ebhuidiao(HttpServletRequest request,HttpServletResponse response) throws Exception{

		String p1_MerId	= request.getParameter("p1_MerId");
		String r0_Cmd  = request.getParameter("r0_Cmd");
		String r1_Code  = request.getParameter("r1_Code");
		String r2_TrxId  = request.getParameter("r2_TrxId");
		//金额
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		//唯一订单号
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid  = request.getParameter("r7_Uid");
		//用户id
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId  =request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate  = request.getParameter("rp_PayDate");
		String rq_CardNo  = request.getParameter("rq_CardNo");
		String ru_Trxtime  = request.getParameter("ru_Trxtime");
		String rq_SourceFee  = request.getParameter("rq_SourceFee");
		String rq_TargetFee  = request.getParameter("rq_TargetFee");
		String hmac	= request.getParameter("hmac");
		String hmac_safe  = request.getParameter("hmac_safe");

		String[] strArr	= {p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType };
		System.out.println("================================================");
		System.out.println("金额："+r3_Amt+"唯一订单号"+r6_Order+"用户id"+r8_MP);
		System.out.println("r5_pid="+request.getParameter("r5_Pid"));
		boolean hmacIsCorrect = YeepayService.verifyCallbackHmac(strArr, hmac);
		boolean hmacsafeIsCorrect = YeepayService.verifyCallbackHmac_safe(strArr, hmac_safe);
		System.out.println("回调参数~~~~~~~~~~strArr~~~~~~~~~~~=" + strArr[0]+"  "+strArr[1]+"  "+strArr[2]+"  "+strArr[3]+"  "+strArr[4]+"  "+strArr[5]+"  "+strArr[6]+"  "+strArr[7]+"  "+strArr[8]+"  "+strArr[9]+"  "+strArr[10]);
		System.out.println("r5_Pid="+strArr[6]);
		System.out.println(hmacIsCorrect+"&&&&"+hmacsafeIsCorrect);
		
		if(hmacIsCorrect && hmacsafeIsCorrect) {
			System.out.println("服务器回调~~~~~~~~~~SUCCESS~~~~~~~~~~~");
			if(r9_BType.equals("2")) {
				
				ExitEntryQuery exitEntryQuery = new ExitEntryQuery();
				
				exitEntryQuery.setPayOrderNumber(r6_Order);
				
				ExitEntryService exitEnser = (ExitEntryService) SpringBeanUtil.getBean("exitEntryServiceImpl");
				
				ClientUserService clientUserService = (ClientUserService) SpringBeanUtil.getBean("clientUserServiceImpl");
				
				//获取用户信息
				ClientUser client = clientUserService.getById(r8_MP);
				if(client!=null){
					try{
						if(client.getClientType()==ClientTypeEnum.Normal){
							//生成订单
							ExitEntry e = new ExitEntry();
							e.setPhoneNumber(client.getPhoneNumber());
							e.setUserId(client.getId());
							e.setEntryTime(new Date());
							e.setEntryMoney(new BigDecimal(Double.parseDouble(r3_Amt)));
							e.setExitOrEntry(ExitOrEntryEnum.EntryPass);
							e.setStatus(EntryStatusEnum.Normal);//普通会员
							e.setPayOrderNumber(r6_Order);
				//			e.setResult(1); //订单初始化
							//保存订单信息
							exitEnser.save(e);
							e.setExitOrEntry(ExitOrEntryEnum.EntryPass);
							//添加金额
							exitEnser.EntryMoney(client.getId(), e);
						}else if(client.getClientType()==ClientTypeEnum.MemberUnit){
							
							ExitEntry e = new ExitEntry();
							e.setPhoneNumber(client.getPhoneNumber());
							e.setUserId(client.getId());
							e.setEntryTime(new Date());
							e.setEntryMoney(new BigDecimal(Double.parseDouble(r3_Amt)));
							e.setExitOrEntry(ExitOrEntryEnum.EntryPass);
							e.setStatus(EntryStatusEnum.Memunit);//会员单位
							e.setPayOrderNumber(r6_Order);
							//保存会员信息
							exitEnser.save(e);
							
							e.setExitOrEntry(ExitOrEntryEnum.EntryPass);
							//添加金额
							exitEnser.EntryMoney(client.getId(), e);
						}
						response.getWriter().write("SUCCESS");
					}catch(Exception e){
						throw new Exception("系统异常"+new Date()+":"+r8_MP+"金额:"+r3_Amt);
					}
				}
			}
		}
		return "/web/callback";
	}

	//入金
	/**
	 * 旧版本
	 * @param enntryri
	 * @param request
	 * @return
	 * @throws Exception
	 */
/*	@RequestMapping("beforePay")
	@ResponseBody
	public ListJson pay(double enntryri,HttpServletRequest request) throws Exception{
		
		ListJson LJ = new ListJson();
		
		ClientUser cu = (ClientUser) request.getSession().getAttribute("loginUser");
		
		if(cu==null){
			LJ.setMessage("请登录");
			LJ.setSuccess(false);
			return LJ;
		}
		
		cu = clientUserService.getById(cu.getId());
		
		String orderNo = (System.currentTimeMillis()+""+(new Random().nextInt(100)+1));
		
		//普通用户
		if(cu.getClientType()==ClientTypeEnum.Normal){
			
			//生成订单
			ExitEntry e = new ExitEntry();
			e.setPhoneNumber(cu.getPhoneNumber());
			e.setUserId(cu.getId());
			e.setEntryTime(new Date());
			e.setEntryMoney(new BigDecimal(enntryri));
			e.setExitOrEntry(ExitOrEntryEnum.EntryApply);
			e.setStatus(EntryStatusEnum.Normal);
			e.setPayOrderNumber(orderNo);
			e.setResult(0); //订单初始化
			service.save(e);
			
			e.setExitOrEntry(ExitOrEntryEnum.EntryPass);
			service.EntryMoney(e.getUserId(), e);
			
		}else if(cu.getClientType()==ClientTypeEnum.MemberUnit){
			
			//会员单位
			
			ExitEntry e = new ExitEntry();
			e.setPhoneNumber(cu.getPhoneNumber());
			e.setUserId(cu.getId());
			e.setEntryTime(new Date());
			e.setEntryMoney(new BigDecimal(enntryri));
			e.setExitOrEntry(ExitOrEntryEnum.EntryApply);
			e.setStatus(EntryStatusEnum.Memunit);
			e.setPayOrderNumber(orderNo);
			service.save(e);
			
			e.setExitOrEntry(ExitOrEntryEnum.EntryPass);
			service.EntryMoney(e.getUserId(), e);
		}
		
		

		String signStr = p1_MerchantNo+orderNo+enntryri+"1入金"+payResultUrl+_key;
		String sign = DigestUtils.md5Hex(signStr);
		StringBuffer str = new StringBuffer();
		str.append("p1_MerchantNo="+p1_MerchantNo);		//商户编号	
		str.append("&p2_OrderNo="+orderNo);				//商户订单号	
		str.append("&p3_Amount="+enntryri);				//订单金额
		str.append("&p4_Cur=1");						//交易币种
		str.append("&p5_ProductName=入金");				//商品名称	
		str.append("&p6_NotifyUrl="+payResultUrl);		//服务器异步通知地址
		str.append("&sign="+sign);				//签名数据
        String response = null;
        
        try {
            response = HttpClientUtil.sendPostHTTP(payUrl, str.toString(), "utf-8");
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
//        JSONObject jsonObject = JSON.parseObject(response);
//        Map<String, String> map = new TreeMap<String, String>();
//
//        map.put("r1_MerchantNo", jsonObject.getString("r1_MerchantNo"));
//        map.put("r2_OrderNo", jsonObject.getString("r2_OrderNo"));
//        map.put("r3_Amount", jsonObject.getString("r3_Amount"));
//        map.put("ra_Status", jsonObject.getString("ra_Status"));
//        map.put("ra_Code", jsonObject.getString("ra_Code"));
//        map.put("rc_CodeMsg", jsonObject.getString("rc_CodeMsg"));
        
//        LJ.setEntity(map);
		LJ.setEntity(200);
		return LJ;
	}*/
	
	//出金申请
	@RequestMapping("ExitMoney")
	@ResponseBody
	public ListJson ExitMoney(double exitri,HttpServletRequest request){
		
		ListJson LJ = new ListJson();
		
		ClientUser cu = (ClientUser) request.getSession().getAttribute("loginUser");
		
		if(cu==null){
			LJ.setMessage("请登录");
			LJ.setSuccess(false);
			return LJ;
		}
		
		cu = clientUserService.getById(cu.getId());
		
		if(cu.getCert()==null ||cu.getCert().equals("")){
			LJ.setMessage("请填写身份证！");
			LJ.setSuccess(false);
			return LJ;
		}
		
		if(cu.getBankCardCode()==null ||cu.getBankCardCode().equals("")){
			LJ.setMessage("请填写银行卡信息！");
			LJ.setSuccess(false);
			return LJ;
		}
		
		if(cu.getName()==null ||cu.getName().equals("")){
			LJ.setMessage("请填写真实姓名！");
			LJ.setSuccess(false);
			return LJ;
		}
		
		try {
			//出金申请
			LJ = service.applyExitMoney(cu, exitri);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LJ;
	}
	
	//入金同步
	@RequestMapping("payResult") 
	public synchronized void  payResult(String r1_MerchantNo,String r2_OrderNo,
			String r3_Amount,String r4_Cur,String r5_Status,
			String ra_PayTime,String rb_DealTime,String sign,HttpServletResponse respone) throws IOException{
		String str = r1_MerchantNo+r2_OrderNo+r3_Amount+r4_Cur+r5_Status+ra_PayTime+rb_DealTime+_key;
		String res = DigestUtils.md5Hex(str);
		if(res.equals(sign)){
			try {
				ExitEntryQuery exitEntryQuery = new ExitEntryQuery();
				exitEntryQuery.setPayOrderNumber(r2_OrderNo);
				ExitEntry e = service.getEntity(exitEntryQuery);
				if(e==null){
					respone.getWriter().write("success");
					return;
				}
				
				//成功
				if(r5_Status.equals("100")){
					if(e.getExitOrEntry()==ExitOrEntryEnum.EntryApply){
						e.setExitOrEntry(ExitOrEntryEnum.EntryPass);	//通过
						//入金
						service.EntryMoney(e.getUserId(), e);
					}
					respone.getWriter().write("success");
					return;
				}else{
					//失败
					e.setExitOrEntry(ExitOrEntryEnum.EntryNotPass);
					service.save(e);
				}
				//业务处理
			} catch (Exception e) {
				respone.getWriter().write("fail");
			}
		}
		respone.getWriter().write("fail");
	}
	
	
	/*//入金回调
	@RequestMapping("payResult") 
	public synchronized void  payResult(String r1_MerchantNo,String r2_OrderNo,
			String r3_Amount,String r4_Cur,String r5_Status,
			String ra_PayTime,String rb_DealTime,String sign,HttpServletResponse respone) throws IOException{
		String str = r1_MerchantNo+r2_OrderNo+r3_Amount+r4_Cur+r5_Status+ra_PayTime+rb_DealTime+_key;
		String res = DigestUtils.md5Hex(str);
		if(res.equals(sign)){
			try {
				ExitEntryQuery exitEntryQuery = new ExitEntryQuery();
				exitEntryQuery.setPayOrderNumber(r2_OrderNo);
				ExitEntry e = service.getEntity(exitEntryQuery);
				if(e==null){
					respone.getWriter().write("success");
					return;
				}
				//成功
				if(r5_Status.equals("100")){
					if(e.getExitOrEntry()==ExitOrEntryEnum.EntryApply){
						e.setExitOrEntry(ExitOrEntryEnum.EntryPass);	//通过
						//入金
						service.EntryMoney(e.getUserId(), e);
					}
					respone.getWriter().write("success");
					return;
				}else{
					//失败
					e.setExitOrEntry(ExitOrEntryEnum.EntryNotPass);
					service.save(e);
				}
				//业务处理
			} catch (Exception e) {
				respone.getWriter().write("fail");
			}
		}
		respone.getWriter().write("fail");
	}*/
	
	
	@RequestMapping("listenOrderNo")
	@ResponseBody
	public ListJson listenOrderNo(String orderNo){
		ListJson LJ = new ListJson();
		Object[] obj = new Object[1];
		obj[0] = orderNo;
		Map<String,Object> map = jdbcTemplate.queryForMap("select * from exitentry where payOrderNumber=?",obj);
		if(Integer.parseInt(map.get("exitOrEntry").toString())==1){
			//成功
			LJ.setSuccess(true);
			LJ.setMessage("1");
			return LJ;
		}else if(Integer.parseInt(map.get("exitOrEntry").toString())==2){
			//失败
			LJ.setSuccess(true);
			LJ.setMessage("0");
			return LJ;
		}else{
			//未响应
			LJ.setSuccess(false);
			return LJ;
		}
	}
	
//	@RequestMapping("listenOrderNo")
//	@ResponseBody
//	public ListJson listenOrderNo(String orderNo){
//		ListJson LJ = new ListJson();
//		Object[] obj = new Object[1];
//		obj[0] = orderNo;
//		Map<String,Object> map = jdbcTemplate.queryForMap("select * from exitentry where payOrderNumber=?",obj);
//		if(Integer.parseInt(map.get("exitOrEntry").toString())==1){
//			//成功
//			LJ.setSuccess(true);
//			LJ.setMessage("1");
//			return LJ;
//		}else if(Integer.parseInt(map.get("exitOrEntry").toString())==2){
//			//失败
//			LJ.setSuccess(true);
//			LJ.setMessage("0");
//			return LJ;
//		}else{
//			//未响应
//			LJ.setSuccess(false);
//			return LJ;
//		}
//	}

	//
	@RequestMapping("payResultJsp")
	public String payResult(Integer msg,HttpServletRequest request){
		request.setAttribute("msg", msg==1?"支付成功":"支付失败");
		return "web/payResult";
	}
	
	
	//出金申请
		/*@RequestMapping("ExitMoney")
		@ResponseBody
		public ListJson ExitMoney(double exitri,HttpSession session,HttpServletRequest request){
			
			ListJson LJ = new ListJson();
			ClientUser cu = (ClientUser) session.getAttribute("loginUser");
			if(cu==null){
				LJ.setSuccess(false);
				LJ.setMessage("请登录");
				return LJ;
			}
			
			if(exitri>50000){
				LJ.setSuccess(false);
				LJ.setMessage("提款金额不超过5万");
				return LJ;
			}
			
			cu = clientUserService.getById(cu.getId());
			
			if(cu.getName()==null || cu.getName().equals("")){
				LJ.setSuccess(false);
				LJ.setMessage("请填写真实姓名");
				return LJ;
			}
			
			if(cu.getBankCardCode()==null || cu.getBankCardCode().equals("")){
				LJ.setSuccess(false);
				LJ.setMessage("请先绑定银行卡");
				return LJ;
			}
			
			if(cu.getCert()==null || cu.getCert().equals("")){
				LJ.setSuccess(false);
				LJ.setMessage("请完善身份证信息");
				return LJ;
			}
			
			//普通用户
			if(cu.getClientType()==ClientTypeEnum.Normal){
				
				//判断
				if(cu.getEnableMoney().doubleValue()<exitri){
					LJ.setSuccess(false);
					LJ.setMessage("可用与余额小于提款金额");
					return LJ;
				}
				
				//订单号
				String orderNo = (System.currentTimeMillis()+""+(new Random().nextInt(100)+1));

				ExitEntry e = new ExitEntry();
				e.setPayOrderNumber(orderNo);
				e.setPhoneNumber(cu.getPhoneNumber());
				e.setUserId(cu.getId());
				e.setExitTime(new Date());
				e.setExitMoney(new BigDecimal(exitri));
				e.setExitOrEntry(ExitOrEntryEnum.ExitApply);	//出金申请
				e.setStatus(EntryStatusEnum.Normal);			//申请中
				
				//申请提现
				String merchantNo = p1_MerchantNo;		//商户编号
				double amount = exitri;		//订单金额
				String trxType = "1";		//交易类型
				String acctType = "1";		//渠道类型
				String acctName = cu.getName();		//收款人姓名
				String acctNo = cu.getBankCardCode();		//收款人账号
				String bankSettNo = "1";			//清算行号
				String bankCode = cu.getBank();		//清算行号
				String mobile = cu.getPhoneNumber();		//开户人手机号
				String certificateCode = cu.getCert();		//开户人身份证号
				
				String beforeSign = merchantNo
						+orderNo
						+amount
						+trxType
						+acctType
						+acctName
						+acctNo
						+bankSettNo
						+bankCode
						+mobile
						+certificateCode
						+retUrl
						+_key;	//密钥
				
				String sign = DigestUtils.md5Hex(beforeSign);
				StringBuffer str = new StringBuffer();
				str.append("merchantNo="+merchantNo);				//商户编号	
				str.append("&orderNo="+orderNo);					//商户订单号	
				str.append("&amount="+amount);						//订单金额
				str.append("&trxType="+trxType);					//交易类型
				str.append("&acctType="+acctType);					//渠道类型	
				str.append("&acctName="+acctName);					//收款人姓名
				str.append("&acctNo="+acctNo);						//收款人账号
				str.append("&bankSettNo="+bankSettNo);				//清算行号
				str.append("&bankCode="+bankCode);					//开户行代码
				str.append("&mobile="+mobile);						//开户人手机号
				str.append("&certificateCode="+certificateCode);	//开户人身份证号
				str.append("&retUrl="+retUrl);						//商户回调地址
				str.append("&sign="+sign);							//签名数据
		        String response = null;
		        
		        try {
		            response = HttpClientUtil.sendPostHTTP(settle, str.toString(), "utf-8");
		            System.out.println(response);
		            JSONObject jsonObject = JSON.parseObject(response);
		            Map<String, String> map = new TreeMap<String, String>();

		            map.put("r1_MerchantNo", jsonObject.getString("r1_MerchantNo"));
		            map.put("r2_OrderNo", jsonObject.getString("r2_OrderNo"));
		            map.put("r3_Amount", jsonObject.getString("r3_Amount"));
		            map.put("ra_Status", jsonObject.getString("ra_Status"));
		            map.put("rc_CodeMsg", jsonObject.getString("rc_CodeMsg"));
		            if(jsonObject.getString("ra_Status").equals("100")){
		            	//成功
		            	service.save(e);
		            }
		            LJ.setSuccess(true);
		            LJ.setEntity(map);
		        } catch (IOException ee) {
		            ee.printStackTrace();
		        }
			}
			
			if(cu.getClientType()==ClientTypeEnum.MemberUnit){
				
				//判断保证金额度
				if(cu.getBailCash().doubleValue()<exitri){
					LJ.setMessage("保证金额度不足");
					LJ.setSuccess(false);
					return LJ;
				}
				
				String orderNo = (System.currentTimeMillis()+""+(new Random().nextInt(100)+1));
				//这里申请出金
				ExitEntry e = new ExitEntry();
				e.setPhoneNumber(cu.getPhoneNumber());
				e.setUserId(cu.getId());
				e.setExitTime(new Date());
				e.setExitMoney(new BigDecimal(exitri));
				e.setExitOrEntry(ExitOrEntryEnum.ExitApply);
				e.setStatus(EntryStatusEnum.Memunit);
				e.setPayOrderNumber(orderNo);
				service.save(e);
				Map<String, String> map = new TreeMap<String, String>();
				map.put("rc_CodeMsg", "出金申请中,等待管理员审核");
				LJ.setSuccess(true);
				LJ.setEntity(map);
				return LJ;
			}
			return LJ;
		}
		
		//代付回调
		@RequestMapping("settleResult")
		public void settleResult(String r1_MerchantNo,
								 String r2_OrderNo,
								 String r3_Amount,
								 String r4_Status,
								 String rb_DealTime,
								 String sign) throws Exception{
			System.out.println(r1_MerchantNo);
			System.out.println(r2_OrderNo);
			System.out.println(r3_Amount);
			System.out.println(r4_Status);
			System.out.println(rb_DealTime);
			System.out.println(sign);
			
			//签名
			String si = DigestUtils.md5Hex(r1_MerchantNo+r2_OrderNo+r3_Amount+r4_Status+rb_DealTime+_key);
			
			if(si.equals(sign)){
				//成功
				ExitEntryQuery exitEntryQuery = new ExitEntryQuery();
				exitEntryQuery.setPayOrderNumber(r2_OrderNo);
				ExitEntry e = service.getEntity(exitEntryQuery);
				
				if(r4_Status.equals("100")){
					//出金操作
					service.outMoney(e.getUserId(), e);
				}else{
					//出金失败
					e.setExitOrEntry(ExitOrEntryEnum.ExitFail);
					service.save(e);
				}
			}
		}*/
    static Logger logger = LoggerFactory.getLogger("business");
	
	public static String accountId = "2120171102100529001";
	
	public static String url = "http://wap.unspay.com:8082/quickpay-front/quickPayWap/prePay?";
	
	public static String backReUrl = "http://www.zhzbfx.com/futures/web/exitEntry/back";		//后台回调
	//public static String backReUrl = "http://yangwch.myqnapcloud.com:8023/futures/web/exitEntry/back";
	public static String reBackReUrl = "http://www.zhzbfx.com/futures/web/exitEntry/reback";		//前台响应
	//public static String reBackReUrl = "http://yangwch.myqnapcloud.com:8023/futures/web/exitEntry/reback";
	//yangwch.myqnapcloud.com:8023
	
	public static String key = "xxk115303021";
	
	//发起支付请求（银生宝）
	@RequestMapping("payReq")
	public void payReq(double enntryri,HttpServletRequest request,HttpServletResponse response) throws IOException{

		ListJson LJ = new ListJson();
		
		ClientUser cu = (ClientUser) request.getSession().getAttribute("loginUser");
		
		if(cu==null){
			LJ.setMessage("请登录");
			LJ.setSuccess(false);
//			return LJ;
		}
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		cu = clientUserService.getById(cu.getId());
		
		String orderNo = (System.currentTimeMillis()+""+(new Random().nextInt(100)+1));
		
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("accountId="+accountId);
		stringBuffer.append("&customerId="+cu.getPhoneNumber());
		stringBuffer.append("&orderNo="+orderNo);
		stringBuffer.append("&commodityName=entry");
		stringBuffer.append("&amount="+df.format(enntryri));
		stringBuffer.append("&responseUrl="+backReUrl);
		stringBuffer.append("&pageResponseUrl="+reBackReUrl);
		
		String mac = MD5Util.MD5(stringBuffer.toString()+"&key="+key).toUpperCase();
		
		stringBuffer.append("&mac="+mac);
		
		//生成申请
		ExitEntry e = new ExitEntry();
		e.setPayOrderNumber(orderNo);
		e.setPhoneNumber(cu.getPhoneNumber());
		e.setUserId(cu.getId());
		e.setEntryTime(new Date());
		e.setEntryMoney(new BigDecimal(enntryri));
		e.setExitOrEntry(ExitOrEntryEnum.EntryApply);	//入金申请
		if(cu.getClientType()==ClientTypeEnum.Normal){
			e.setStatus(EntryStatusEnum.Normal);			//申请中
			service.save(e);
		}else if(cu.getClientType()==ClientTypeEnum.MemberUnit){
			e.setStatus(EntryStatusEnum.Memunit);
			service.save(e);
		}
		//重定向
		response.sendRedirect(url+stringBuffer);
	}
	
	
	//支付回调（银生宝）
	@RequestMapping("back")
	public void back(String result_code,
					 String result_msg,
					 String orderNo,
					 String userId,
					 String bankName,
					 String tailNo,
					 String amount,
					 String mac,
					 HttpServletResponse response) throws UnsupportedEncodingException{
		
		System.out.println(result_code);
		System.out.println(result_msg);
		System.out.println(orderNo);
		System.out.println(userId);
		System.out.println(bankName);
		System.out.println(tailNo);
		System.out.println(amount);
		System.out.println(mac);

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("accountId="+accountId);
		stringBuffer.append("&orderNo="+orderNo);
		stringBuffer.append("&userId="+userId);
		stringBuffer.append("&bankName="+bankName);
		stringBuffer.append("&tailNo="+tailNo);
		stringBuffer.append("&amount="+amount);
		stringBuffer.append("&result_code="+result_code);
		stringBuffer.append("&result_msg="+result_msg);
		stringBuffer.append("&key="+key);
		
		String md5 = MD5Util.MD5(new String(stringBuffer.toString().getBytes("UTF-8")));
		
		if(mac.equals(md5)){
			logger.info("订单号："+orderNo+",状态："+result_code);
			ExitEntryQuery entryQuery = new ExitEntryQuery();
			entryQuery.setPayOrderNumber(orderNo);
			ExitEntry e = service.getEntity(entryQuery);
			if(e!=null){
				logger.info("订单号："+orderNo+",状态："+result_code+",userId:"+userId+",金额:"+e.getEntryMoney());
			}
			//必须未申请状态
			if(e!=null && e.getExitOrEntry()==ExitOrEntryEnum.EntryApply && result_code.equals("0000")){
				try {
					service.EntryMoney(e.getUserId(), e);
					response.getWriter().write("SUCCESS");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}		
	}
	
	//支付请求完毕后跳转（银生宝）
	@RequestMapping("reback")
	public String reback(String orderNo,
						 String userId,
						 String bankName,
						 String tailNo,
						 String amount,
						 String mac,
						 HttpServletRequest request){
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("accountId="+accountId);
		stringBuffer.append("&orderNo="+orderNo);
		stringBuffer.append("&userId="+userId);
		stringBuffer.append("&bankName="+bankName);
		stringBuffer.append("&tailNo="+tailNo);
		stringBuffer.append("&amount="+amount);
		stringBuffer.append("&key="+key);
		if(MD5Util.MD5(stringBuffer.toString()).toUpperCase().equals(mac)){
			return "redirect:index";
		}
		return "redirect:index";
	}
	
	
	@RequestMapping("index")
	public String index(){
		return "web/index";
	}
	
	//本地
	//static String backUrlForRongFu = "http://xmszit.tpddns.cn:8023/futures/web/exitEntry/rebackRongFu";
	//线上
	static String backUrlForRongFu = "http://www.zhzbfx.com/futures/web/exitEntry/rebackRongFu";
	
	//预支付请求（融付宝）步骤1
	@RequestMapping("payRongFu")
	@ResponseBody
	public ListJson payRongFu(double enntryri,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		ListJson LJ = new ListJson();
		
		ClientUser cu = (ClientUser) request.getSession().getAttribute("loginUser");
		
		if(cu==null){
			LJ.setMessage("请登录");
			LJ.setSuccess(false);
			return LJ;
		}
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		cu = clientUserService.getById(cu.getId());
		
		if(!(StringUtils.isNotEmpty(cu.getBankCardType()) && (cu.getBankCardType().equals("00") || cu.getBankCardType().equals("01")))){
			/*LJ.setMessage("请设置好您的银行卡类型");
			LJ.setSuccess(false);
			return LJ;*/
			
			//默认是储蓄卡
			cu.setBankCardType("01");
		}
		
		//参数传递
		Map param = new HashMap();
        
		param.put("userId", "20180102095418488680");	
        param.put("orderAmt", df.format(enntryri));
        param.put("payType", "02");
        param.put("payMode", "2");
        param.put("backUrl", backUrlForRongFu);
        param.put("openid", "");
        param.put("name", cu.getName());
        param.put("cardNo", cu.getBankCardCode());		//银行卡号码
        param.put("cardType", cu.getBankCardType());	//卡的类型
        param.put("expireDate", "2501");
        param.put("cvv", "");
        param.put("mobile", cu.getPhoneNumber());		//手机
        param.put("idNo", cu.getCert());				//身份证
        param.put("transFlag", "01");
        param.put("transNums", "0");
        param.put("orderId", "");
        param.put("tradeNo", "");
        param.put("vcode", "");
        param.put("sign", SignKit.sign("2FE255", param));
        param.put("key", "2FE255");
        String url = "http://app.xmfastpay.com:81/fpc/trade/quotaPay";
        String context = HttpClientUtils.post(HttpClientUtils.createHttpClient(), url, param, HttpClientUtils.DEFAULT_ENCODING);
        System.out.println("同步返回:" + context);

        JSONObject jsonParam = JSONObject.parseObject(context);
        Map param1 = new HashMap();
        // 状态码 0成功 其他失败
        
        if(jsonParam.containsKey("code") && jsonParam.getString("code").equals("0")){
        	//成功的话
        	//生成订单记录
        	
        	//普通用户
        	ExitEntry e = new ExitEntry();
    		e.setPayOrderNumber(jsonParam.getString("orderId"));	//订单号
    		e.setPhoneNumber(cu.getPhoneNumber());
    		e.setUserId(cu.getId());
    		e.setEntryTime(new Date());
    		e.setSerials(jsonParam.getString("tradeNo"));	//流水号
    		e.setEntryMoney(new BigDecimal(enntryri));
    		e.setExitOrEntry(ExitOrEntryEnum.EntryApply);	//入金申请
    		if(cu.getClientType()==ClientTypeEnum.Normal){
    			e.setStatus(EntryStatusEnum.Normal);			//申请中
    			service.save(e);
    		}else if(cu.getClientType()==ClientTypeEnum.MemberUnit){
    			e.setStatus(EntryStatusEnum.Memunit);
    			service.save(e);
    		}
    		request.getSession().setAttribute("status", "OK");
    		request.getSession().setAttribute("entryRecord", e);
    		request.getSession().setAttribute("userforPay", cu);
    		LJ.setSuccess(true);
    		return LJ;
        }else{
        	//预请求失败
        	LJ.setSuccess(false);
        	LJ.setMessage(jsonParam.getString("msg"));
        	return LJ;
        }
	}
	
	//支付页面（融付宝）步骤2
	@RequestMapping("payRongFuPage")
	public String payRongFuPage(HttpServletRequest request,HttpServletResponse response){
		try{
			/*String status = (String) request.getSession().getAttribute("status");
			ExitEntry e = (ExitEntry) request.getSession().getAttribute("entryRecord");
			ClientUser cu = (ClientUser) request.getSession().getAttribute("userforPay");
			
			request.setAttribute("status", status);
    		request.setAttribute("entryRecord", e);
    		request.setAttribute("userforPay", cu);*/
    		
			return "/web/rongFuPay";
		}catch(Exception e){
			return "redirect:index";
		}
	}
	
	//支付请求（融付宝）步骤3
	@RequestMapping("payRongFuPay")
	@ResponseBody
	public ListJson payRongFuPay(String orderId,
								 String vcode,
								 HttpServletRequest request,
								 HttpServletResponse response) throws IOException{
		
		ListJson LJ = new ListJson();
		
		ExitEntryQuery exitEntryQuery = new ExitEntryQuery();
		exitEntryQuery.setPayOrderNumber(orderId);
		ExitEntry e = service.getEntity(exitEntryQuery);
		DecimalFormat df = new DecimalFormat("#.00");

		if(e!=null){
			//正式请求支付
			Map param1 = new HashMap();
	 		param1.put("userId", "20180102095418488680");
        	param1.put("orderAmt", df.format(e.getEntryMoney()));
        	param1.put("payType", "02");
        	param1.put("payMode", "3");
        	param1.put("backUrl", backUrlForRongFu);
 	        // 商户订单号
        	param1.put("orderId", e.getPayOrderNumber());
 	        // 预支付流水号
        	param1.put("tradeNo", e.getSerials());
 	        // 短信验证码
        	param1.put("vcode", vcode);
        	param1.put("sign", SignKit.sign("2FE255", param1));
        	param1.put("key", "2FE255");
	        String url = "http://app.xmfastpay.com:81/fpc/trade/quotaPay";
        	String results = HttpClientUtils.post(HttpClientUtils.createHttpClient(), url, param1, HttpClientUtils.DEFAULT_ENCODING);
	        System.out.println("同步返回:" + results);
	        JSONObject jsonParam = JSONObject.parseObject(results);
	        if(jsonParam.containsKey("code") && jsonParam.getString("code").equals("0")){
	        	LJ.setMessage("支付请求成功，请等待处理！！");
	        	LJ.setSuccess(true);
	        	return LJ;
	        }else{
	        	LJ.setMessage(jsonParam.getString("msg"));
	        	LJ.setSuccess(false);
	        	return LJ;
	        }
		}else{
			LJ.setSuccess(false);
			LJ.setMessage("该订单不存在！！");
			return LJ;
		}
	}
	
	
	//支付回调（融付宝）步骤4
	@RequestMapping("rebackRongFu")
	public void rebackRongFu(String orderId,
							 String orderAmt,
							 String payTime,
							 String payType,
							 String code,
							 String msg,
							 String sign,
							 String key,
							 HttpServletRequest request,
							 HttpServletResponse response){
		
		logger.info("异步回调参数     orderId："+orderId
				+"orderAmt:"+orderAmt
				+"payTime:"+payTime
				+"payType:"+payType
				+"code:"+code
				+"msg:"+msg);
		
		//签名验证
        Map<String,Object> param1 = new HashMap<String,Object>();
        
        param1.put("orderId", orderId);
        param1.put("orderAmt", orderAmt);
        param1.put("payTime", payTime);
        param1.put("payType", payType);
        param1.put("code", code);
		param1.put("msg", msg);

        String signs = SignKit.sign("2FE255", param1);
		
        logger.info("融付宝入金异步回调：单号"+orderId+",状态："+code+",签名:"+sign+",本地签名结果:"+signs);
        
		if(code.equals("0") && sign.equals(signs)){
			//查订单
			ExitEntryQuery exitEntryQuery = new ExitEntryQuery();
			exitEntryQuery.setPayOrderNumber(orderId);
			ExitEntry e = service.getEntity(exitEntryQuery);
			//申请状态才能入金
			if(e!=null && e.getExitOrEntry()==ExitOrEntryEnum.EntryApply){
				try {
					logger.info("融付宝入金：单号"+orderId+",异步通知成功");
					service.EntryMoney(e.getUserId(), e);
					response.getWriter().write("SUCCESS");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static String EncoderByMd5(String str) {  
	    String result = "";  
	    MessageDigest md5 = null;  
	    try {  
	        md5 = MessageDigest.getInstance("MD5");  
	        // 这句是关键  
	        md5.update(str.getBytes("UTF-8"));
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();  
	    } catch (UnsupportedEncodingException e) {  
	        e.printStackTrace();
	    }  
	    byte b[] = md5.digest();  
	    int i;  
	    StringBuffer buf = new StringBuffer("");  
	    for (int offset = 0; offset < b.length; offset++) {  
	        i = b[offset];  
	        if (i < 0)  
	            i += 256;  
	        if (i < 16)  
	            buf.append("0");  
	        buf.append(Integer.toHexString(i));  
	    }  
	    result = buf.toString();  
	  
	    return result;  
	}
	
	@RequestMapping("Test")
	public void test(){
		 Map<String,Object> param1 = new HashMap<String,Object>();
		  	param1.put("orderId", "20180108151018854308");
		    param1.put("orderAmt", "10.00");
		    param1.put("payTime", "2018-01-08 15:10:18000");
		    param1.put("payType", "02");
		    param1.put("code", "0"); 
		    param1.put("msg", "交易成功");
		    String signs = "";
			signs = SignKit.sign("2FE255", param1);
		    System.out.println(signs);
	}
	
	public static void main(String[] args) {
		 Map<String,Object> param1 = new HashMap<String,Object>();
		  	param1.put("orderId", "20180108151018854308");
		    param1.put("orderAmt", "10.00");
		    param1.put("payTime", "2018-01-08 15:10:18000");
		    param1.put("payType", "02");
		    param1.put("code", "0"); 
		    param1.put("msg", "交易成功");
		    String signs = "";
			signs = SignKit.sign("2FE255", param1);
		    System.out.println(signs);
	}
}