package com.xmxnkj.voip.voice.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.service.AppBaseService;
import com.xmxnkj.voip.voice.entity.CallTimeSet;
import com.xmxnkj.voip.voice.entity.VoiceTemplate;
import com.xmxnkj.voip.voice.entity.emun.CallTimeState;
import com.xmxnkj.voip.voice.entity.emun.OperationState;
import com.xmxnkj.voip.voice.entity.query.CallTimeSetQuery;
import com.xmxnkj.voip.voice.entity.query.VoiceTemplateQuery;
import com.xmxnkj.voip.voice.service.ICallTimeSetService;
import com.xmxnkj.voip.voice.service.IVoiceTemplateService;
import com.xmxnkj.voip.web.BaseController;
import com.xmxnkj.voip.web.models.ResultJson;

@Controller
@RequestMapping("/voice/calltime")
public class CallTimeSetController extends BaseController<CallTimeSet, CallTimeSetQuery, CallTimeSet> {

	@Autowired
	private ICallTimeSetService service;

	@Override
	protected ICallTimeSetService getService() {
		// TODO Auto-generated method stub
		return service;
	}

	/**
	 * 根据id查询一条数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	@ResponseBody
	public ResultJson findById(String id) {
		ResultJson rj = new ResultJson();
		try {
			CallTimeSet time = service.findById(id);
			if (time != null) {
				rj.setSuccess(true);
				rj.setMessage("成功");
				rj.setEntity(time);
				return rj;
			} else {
				rj.setSuccess(false);
				rj.setMessage("查无数据");
				return rj;
			}
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setMessage("失败，" + e.getMessage());
			return rj;
		}
	}

	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	@RequestMapping("/find")
	@ResponseBody
	public ResultJson find(String clientUserId) {
		ResultJson rj = new ResultJson();
		try {
			//List<CallTimeSet> voices = service.find();
			CallTimeSetQuery callTimeSetQuery = new CallTimeSetQuery();
			if(StringUtils.isNotEmpty(clientUserId)){
				callTimeSetQuery.setClientUserId(clientUserId);
			}
			callTimeSetQuery.setClientId(getLoginClientId());
			List<CallTimeSet> voices = service.getEntities(callTimeSetQuery);
			if (voices != null && voices.size() > 0) {
				rj.setSuccess(true);
				rj.setMessage("成功");
				rj.setEntity(voices);
				return rj;
			} else {
				rj.setSuccess(false);
				rj.setMessage("查无数据");
				return rj;
			}
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setMessage("失败，" + e.getMessage());
			return rj;
		}
	}

	/**
	 * 添加一条数据
	 * 
	 * @param time
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ResultJson add(String startTime, String endTime) {
		ResultJson rj = new ResultJson();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		try {
			Date start = sdf.parse(startTime);
			Date end = sdf.parse(endTime);
			boolean b1 = end.getTime() > start.getTime();//判断结束时间是否大于开始时间
			if(!b1) {
				rj.setSuccess(false);
				rj.setMessage("结束时间必须大于开始时间");
				return rj;
			}
			boolean b = service.between(start, end,getLoginClientId(),getLoginClientUserId());//判断该时间段是否与原有的数据有冲突
			if (!b) {
				rj.setSuccess(false);
				rj.setMessage("该时间段有冲突");
				return rj;
			}

			CallTimeSet time = new CallTimeSet();
			time.setClientId(getLoginClientId());
			time.setStartTime(start);
			time.setEndTime(end);
			time.setState(CallTimeState.All);
			time.setCreateTime(new Date());
			time.setOperation(OperationState.Open);
			time.setClientUserId(getLoginClientUserId());
			service.add(time);
			rj.setSuccess(true);
			rj.setMessage("添加成功");
			return rj;
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setMessage("添加失败，" + e.getMessage());
			return rj;
		}
	}

	/**
	 * 切换操作状态
	 * 
	 * @param id
	 * @param start
	 * @param end
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ResultJson update(String id) {
		ResultJson rj = new ResultJson();
		try {
			CallTimeSet time = service.getEntityById(id);
			if (time != null) {
				if (time.getOperation() != null && time.getOperation().equals(OperationState.Open)) {
					time.setOperation(OperationState.Close);
				} else {
					time.setOperation(OperationState.Open);
				}
				service.add(time);
				rj.setSuccess(true);
				rj.setMessage("更新成功");
				return rj;
			} else {
				rj.setSuccess(false);
				rj.setMessage("该id不存在");
				return rj;
			}

		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setMessage("更新失败，" + e.getMessage());
			return rj;
		}
	}

	@RequestMapping("/delete")
	@ResponseBody
	public ResultJson delete(String id) {
		ResultJson rj = new ResultJson();
		try {
			service.deleteById(id);// 逻辑删除
			rj.setSuccess(true);
			rj.setMessage("删除成功");
			return rj;
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setMessage("删除失败，" + e.getMessage());
			return rj;
		}
	}
}