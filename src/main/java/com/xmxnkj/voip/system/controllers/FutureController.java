package com.xmxnkj.voip.system.controllers;

import com.xmxnkj.voip.system.entity.Future;
import com.xmxnkj.voip.system.entity.query.FutureQuery;
import com.xmxnkj.voip.system.service.voipervice;
import com.xmxnkj.voip.system.timer.TaskTimer;
import com.xmxnkj.voip.web.BaseController;
import com.xmxnkj.voip.web.models.ListJson;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/back/future"})
public class FutureController
  extends BaseController<Future, FutureQuery>
{
  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private voipervice service;
  
  public voipervice getService()
  {
    return this.service;
  }
  
  @RequestMapping({"/listJsp"})
  public ModelAndView listJsp()
  {
    return new ModelAndView("future/list");
  }
  
  @RequestMapping({"/listData"})
  @ResponseBody
  public ListJson listData(Integer rows, Integer page){
    try{
      FutureQuery futureQuery = new FutureQuery();
      futureQuery.setPage(page.intValue(), rows.intValue());
      List<Future> list = this.service.getEntities(futureQuery);
      ListJson LJ = new ListJson(list);
      LJ.setPaging(futureQuery.getPaging());
      return LJ;
    }catch (Exception e){
      e.printStackTrace();
      return new ListJson(e);
    }
  }
  
  @RequestMapping({"/editJsp"})
  public ModelAndView editJsp(){
    return new ModelAndView("future/edit");
  }
  
  @RequestMapping(value={"/saveData"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public ListJson saveData(Future future){
    future.setStopOutTimeOne(future.getStopOutTimeOne().substring(future.getStopOutTimeOne().indexOf(" ") + 1, future.getStopOutTimeOne().length()));
    future.setStopOutTimeTwo(future.getStopOutTimeTwo().substring(future.getStopOutTimeTwo().indexOf(" ") + 1, future.getStopOutTimeTwo().length()));
    
    future.setStartTime1(future.getStartTime1().substring(future.getStartTime1().indexOf(" ") + 1, future.getStartTime1().length()));
    future.setStartTime2(future.getStartTime2().substring(future.getStartTime2().indexOf(" ") + 1, future.getStartTime2().length()));
    future.setStartTime3(future.getStartTime3().substring(future.getStartTime3().indexOf(" ") + 1, future.getStartTime3().length()));
    future.setStartTime4(future.getStartTime4().substring(future.getStartTime4().indexOf(" ") + 1, future.getStartTime4().length()));
    
    future.setEndTime1(future.getEndTime1().substring(future.getEndTime1().indexOf(" ") + 1, future.getEndTime1().length()));
    future.setEndTime2(future.getEndTime2().substring(future.getEndTime2().indexOf(" ") + 1, future.getEndTime2().length()));
    future.setEndTime3(future.getEndTime3().substring(future.getEndTime3().indexOf(" ") + 1, future.getEndTime3().length()));
    future.setEndTime4(future.getEndTime4().substring(future.getEndTime4().indexOf(" ") + 1, future.getEndTime4().length()));
    try{
      if ((future.getId() == null) || (future.getId().equals(""))){
        this.service.save(future);
      }else{
        Future f = (Future)this.service.getById(future.getId());
        f.setFutureCode(future.getFutureCode());
        f.setName(future.getName());
        f.setTiggerOffset(future.getTiggerOffset());
        f.setTiggerStop(future.getTiggerStop());
        f.setBusinessCount(future.getBusinessCount());
        f.setUnit(future.getUnit());
        f.setType(future.getType());
        f.setExchangeCommission(future.getExchangeCommission());
        f.setMinSandards(future.getMinSandards());
        f.setExchangeMarginRatio(future.getExchangeMarginRatio());
        f.setRateOfInformationService(future.getRateOfInformationService());
        if ((future.getStopOutTimeOne() != null) && (!future.getStopOutTimeOne().equals(""))) {
          f.setStopOutTimeOne(future.getStopOutTimeOne());
        }
        if ((future.getStopOutTimeTwo() != null) && (!future.getStopOutTimeTwo().equals(""))) {
          f.setStopOutTimeTwo(future.getStopOutTimeTwo());
        }
        f.setMinPriceChange(future.getMinPriceChange());
        if ((future.getStartTime1() != null) && (!future.getStartTime1().equals(""))) {
          f.setStartTime1(future.getStartTime1());
        }
        if ((future.getEndTime1() != null) && (!future.getEndTime1().equals(""))) {
          f.setEndTime1(future.getEndTime1());
        }
        if ((future.getStartTime2() != null) && (!future.getStartTime2().equals(""))) {
          f.setStartTime2(future.getStartTime2());
        }
        if ((future.getEndTime2() != null) && (!future.getEndTime2().equals(""))) {
          f.setEndTime2(future.getEndTime2());
        }
        if ((future.getStartTime3() != null) && (!future.getStartTime3().equals(""))) {
          f.setStartTime3(future.getStartTime3());
        }
        if ((future.getEndTime3() != null) && (!future.getEndTime3().equals(""))) {
          f.setEndTime3(future.getEndTime3());
        }
        if ((future.getStartTime4() != null) && (!future.getStartTime4().equals(""))) {
          f.setStartTime4(future.getStartTime4());
        }
        if ((future.getEndTime4() != null) && (!future.getEndTime4().equals(""))) {
          f.setEndTime4(future.getEndTime4());
        }
        this.service.save(f);
        

        List<Map<String, Object>> list = this.jdbcTemplate.queryForList("select name,stopOutTimeOne,stopOutTimeTwo from future");
        TaskTimer.set.clear();
        for (Map<String, Object> map : list)
        {
          TaskTimer.set.add(map.get("stopOutTimeOne").toString());
          TaskTimer.set.add(map.get("stopOutTimeTwo").toString());
        }
      }
      return new ListJson(true);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return new ListJson(e);
    }
  }
}
