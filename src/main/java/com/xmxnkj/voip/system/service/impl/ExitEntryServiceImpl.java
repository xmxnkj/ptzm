package com.xmxnkj.voip.system.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsit.common.service.AppBaseServiceImpl;
import com.xmxnkj.voip.system.dao.ExitEntryDao;
import com.xmxnkj.voip.system.entity.ClientUser;
import com.xmxnkj.voip.system.entity.ExitEntry;
import com.xmxnkj.voip.system.entity.emun.ClientTypeEnum;
import com.xmxnkj.voip.system.entity.emun.EntryStatusEnum;
import com.xmxnkj.voip.system.entity.emun.ExitOrEntryEnum;
import com.xmxnkj.voip.system.entity.query.ExitEntryQuery;
import com.xmxnkj.voip.system.service.ClientUserService;
import com.xmxnkj.voip.system.service.ExitEntryService;
import com.xmxnkj.voip.web.models.ListJson;

@Service
@Transactional
public class ExitEntryServiceImpl extends AppBaseServiceImpl<ExitEntry, ExitEntryQuery> implements ExitEntryService{
	
	static Logger logger = LoggerFactory.getLogger("business");

	@Autowired
	private ClientUserService clientUserService;
	
	@Autowired
	private ExitEntryDao dao;

	@Override
	public ExitEntryDao getDao() {
		return dao;
	}
	
	//入金成功操作
	@Override
	public synchronized void EntryMoney(String userId,ExitEntry e) throws Exception{
		try{
			//再一次
        	ExitEntry exitEntry = getById(e.getId());
        	if(exitEntry.getExitOrEntry()==ExitOrEntryEnum.EntryPass){
        		return;
        	}
			e.setExitOrEntry(ExitOrEntryEnum.EntryPass);
			ClientUser cu = clientUserService.getById(userId);
			if(cu.getClientType()==ClientTypeEnum.Normal){
				logger.info("入金前（用户）********用户手机："
							+cu.getPhoneNumber()+":余额："
							+cu.getEnableMoney()+",额度："
							+e.getEntryMoney());
				cu.setEnableMoney(cu.getEnableMoney().add(e.getEntryMoney()));
				
				logger.info("入金后（用户）********用户手机："
						+cu.getPhoneNumber()+":余额："
						+cu.getEnableMoney());
			}
			if(cu.getClientType()==ClientTypeEnum.MemberUnit){
				logger.info("入金前（会员）********手机："
						+cu.getPhoneNumber()+":余额："
						+cu.getEnableBailCash()+",额度："
						+e.getEntryMoney());
				//保证金额度 ++
				cu.setBailCash(cu.getBailCash().add(e.getEntryMoney()));
				//可用保证金  = 额度  - 占用 
				cu.setEnableBailCash(cu.getBailCash().subtract(cu.getSeatBailCash()));
				logger.info("入金后（会员）********手机："
						+cu.getPhoneNumber()+":余额："
						+cu.getEnableBailCash()+",额度："
						+e.getEntryMoney());
			}
			save(e);
			clientUserService.save(cu);
		}catch(Exception ee){
			
		}
	}
	
	//出金成功
	@Override
	public synchronized void outMoney(String userId, ExitEntry e) throws Exception {
		try{
			//再一次
        	ExitEntry exitEntry = getById(e.getId());
        	if(exitEntry.getExitOrEntry()==ExitOrEntryEnum.ExitSuccess){
        		return;
        	}
        	
			ClientUser cu = clientUserService.getById(userId);
			if(cu==null){
				return;
			}
			//普通会员出金
			if(cu.getClientType()==ClientTypeEnum.Normal){
				
				if(e.getExitOrEntry()==ExitOrEntryEnum.ExitApplyPass){
					/*cu.setEnableMoney(cu.getEnableMoney().subtract(e.getExitMoney()));
					clientUserService.save(cu);*/
					logger.info("出金成功********手机："
							+cu.getPhoneNumber()+":余额："
							+cu.getEnableMoney()+",额度："
							+e.getExitMoney());
					
					e.setExitOrEntry(ExitOrEntryEnum.ExitSuccess);
					save(e);
				}
			}
			
			//会员单位
			if(cu.getClientType()==ClientTypeEnum.MemberUnit){
				//保证金额度
				if(e.getExitOrEntry()==ExitOrEntryEnum.ExitApplyPass){
					
					//cu.setBailCash(cu.getBailCash().subtract(e.getExitMoney()));
					//可用保证金  = 额度  - 占用 
					//cu.setEnableBailCash(cu.getBailCash().subtract(cu.getSeatBailCash()));
					//clientUserService.save(cu);
					
					logger.info("出金成功********手机："
							+cu.getPhoneNumber()+":余额："
							+cu.getEnableBailCash()+",额度："
							+e.getExitMoney());
					
					e.setExitOrEntry(ExitOrEntryEnum.ExitSuccess);
					save(e);
				}
			}
		}catch(Exception ee){
			
		}
	}
	
	//申请出金
	@Override
	public ListJson applyExitMoney(ClientUser cu, double exitri)
			throws Exception {
		
		ListJson LJ = new ListJson();
		try{
			//普通会员
			if(cu.getClientType()==ClientTypeEnum.Normal){
				//出金2元手续费
				if(cu.getEnableMoney().doubleValue() < exitri){
					LJ.setMessage("余额不足！");
					LJ.setSuccess(false);
					return LJ;
				}
				ExitEntry e = new ExitEntry();
				e.setPhoneNumber(cu.getPhoneNumber());
				e.setUserId(cu.getId());
				e.setExitTime(new Date());
				e.setExitMoney(new BigDecimal(exitri).subtract(new BigDecimal(2)));
				e.setExitOrEntry(ExitOrEntryEnum.ExitApply);	//出金申请
				e.setStatus(EntryStatusEnum.Normal);
				e.setBankCardNo(cu.getBankCardCode());
				e.setName(cu.getName());
				e.setBankAddress(cu.getBankAddress());
				save(e);
				
				//冻结金额
				logger.info("出金申请冻结（用户）********手机："
						+cu.getPhoneNumber()+":余额："
						+cu.getEnableMoney()+",额度："
						+e.getExitMoney());
				cu.setEnableMoney(cu.getEnableMoney().subtract(e.getExitMoney().add(new BigDecimal(2))));
				clientUserService.save(cu);
				
				logger.info("出金申请冻结（用户）********手机："
						+cu.getPhoneNumber()+":余额："
						+cu.getEnableMoney()+",额度："
						+e.getExitMoney());
				
				LJ.setMessage("已申请出金！");
				LJ.setSuccess(true);
				return LJ;
			}
			
			//会员单位
			if(cu.getClientType()==ClientTypeEnum.MemberUnit){
				if(cu.getBailCash().doubleValue() < exitri){
					LJ.setMessage("保证金额度不足！");
					LJ.setSuccess(false);
					return LJ;
				}
				ExitEntry e = new ExitEntry();
				e.setPhoneNumber(cu.getPhoneNumber());
				e.setUserId(cu.getId());
				e.setExitTime(new Date());
				e.setExitMoney(new BigDecimal(exitri).subtract(new BigDecimal(2)));
				e.setExitOrEntry(ExitOrEntryEnum.ExitApply);	//出金申请
				e.setStatus(EntryStatusEnum.Memunit);
				e.setBankCardNo(cu.getBankCardCode());
				e.setName(cu.getName());
				e.setBankAddress(cu.getBankAddress());
				save(e);
				logger.info("出金申请冻结（会员）********手机："
						+cu.getPhoneNumber()+"保证金："
						+cu.getEnableMoney()+",额度："
						+e.getExitMoney());
				//冻结金额
				cu.setBailCash(cu.getBailCash().subtract(e.getExitMoney().add(new BigDecimal(2))));
				//可用保证金  = 额度  - 占用 
				cu.setEnableBailCash(cu.getBailCash().subtract(cu.getSeatBailCash()));
				clientUserService.save(cu);
				
				logger.info("出金申请冻结（会员）********手机："
						+cu.getPhoneNumber()+":保证金："
						+cu.getEnableBailCash()+",额度："
						+e.getExitMoney());
				
				LJ.setMessage("已申请出金！");
				LJ.setSuccess(true);
				return LJ;
			}
			return LJ;
		}catch(Exception ee){
			
		}
		return LJ;
	}
	
	//出金失败
	@Override
	public synchronized ListJson failExitMoney(ClientUser cu,ExitEntry e) throws Exception {
		
		
		//再一次
    	ExitEntry exitEntry = getById(e.getId());
    	if(exitEntry.getExitOrEntry()==ExitOrEntryEnum.ExitSuccess){
    		return null;
    	}
		
		//普通会员
		if(cu.getClientType()==ClientTypeEnum.Normal){
			cu.setEnableMoney(cu.getEnableMoney().add(e.getExitMoney().add(new BigDecimal(2))));
			clientUserService.save(cu);
			logger.info("出金失败********手机："
					+cu.getPhoneNumber()+":余额："
					+cu.getEnableMoney()+",额度："
					+e.getExitMoney());
		}
		
		//会员单位
		if(cu.getClientType()==ClientTypeEnum.MemberUnit){
			cu.setBailCash(cu.getBailCash().add(e.getExitMoney().add(new BigDecimal(2))));
			//可用保证金  = 额度  - 占用 
			cu.setEnableBailCash(cu.getBailCash().subtract(cu.getSeatBailCash()));
			clientUserService.save(cu);
			logger.info("出金失败********手机："
					+cu.getPhoneNumber()+"保证金："
					+cu.getEnableMoney()+",额度："
					+e.getExitMoney());
		}	
		return null;
	}
}