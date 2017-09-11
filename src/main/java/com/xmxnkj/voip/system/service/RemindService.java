package com.xmxnkj.voip.system.service;



import java.util.List;

import com.hsit.common.service.BusinessBaseService;
import com.xmxnkj.voip.client.entity.Client;
import com.xmxnkj.voip.system.entity.PublishNotice;
import com.xmxnkj.voip.system.entity.Remind;
import com.xmxnkj.voip.system.entity.query.RemindQuery;

/**
 * @ProjectName:voip
 * @ClassName: RemindService
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public interface RemindService extends BusinessBaseService<Remind, RemindQuery>{

	public List<PublishNotice> updateNotice(Client byId);

}
