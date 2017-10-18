package com.xmxnkj.voip.system.service;

import com.hsit.common.service.AppBaseService;
import com.xmxnkj.voip.system.entity.Future;
import com.xmxnkj.voip.system.entity.OrderItem;
import com.xmxnkj.voip.system.entity.query.OrderItemQuery;
import com.xmxnkj.voip.web.models.ListJson;

public interface OrderItemService
  extends AppBaseService<OrderItem, OrderItemQuery>
{
  //平仓
  public ListJson eveningUp(String paramString, boolean paramBoolean);
  //判断是否过了交易时间
  public boolean beforeEvengUp(Future paramFuture);
  //平仓回调后的业务
  public void eveningUpAfter(String paramString1, String paramString2, String paramString3, String paramString4, Integer paramInteger1, Integer paramInteger2, String paramString5, String paramString6, String paramString7, Integer paramInteger3);
  //自动撤单
  public void killOrder(String orderID,String orderNo);
}
