package com.xmxnkj.voip.client.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmxnkj.voip.client.dao.DeptDao;
import com.xmxnkj.voip.client.entity.ClientUser;
import com.xmxnkj.voip.client.entity.Dept;
import com.xmxnkj.voip.client.entity.query.DeptQuery;
import com.xmxnkj.voip.client.service.ClientUserService;
import com.xmxnkj.voip.client.service.DeptService;
import com.xmxnkj.voip.web.models.ListJson;

@Service
@Transactional
public class DeptServiceImpl extends BusinessBaseServiceImpl<Dept, DeptQuery> implements DeptService{
	
	@Autowired
	private DeptDao dao;
	
	@Autowired
	private ClientUserService clientUserService;
 
	@Override
	public DeptDao getDao(){
		return dao;
	}

	@Override
	public boolean hasSonDept(String deptId) {
		DeptQuery query = new DeptQuery();
		query.setDpid(deptId);
		query.setDeleted(false);
		
		return getEntityCount(query)>0;
	}
	


	@Override
	public void randomCode(Dept dept) {
		boolean flag = false;
		while(!flag){
			String code = getStringRandom(5).toUpperCase();
			DeptQuery deptQuery = new DeptQuery();
			deptQuery.setCode(code);
			deptQuery.setDeleted(false);
			if(getEntityCount(deptQuery)==0){
				flag = true;
				dept.setCode(code);
			}
		}
	}
	
	public String getStringRandom(int length) {  
        String val = "";  
        Random random = new Random();  
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) {  
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母
            int temp = random.nextInt(2)%2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  

    }

	@Override
	public void flushManager(Dept dept) {
		if(dept!=null && StringUtils.isNotEmpty(dept.getClientUserId())){
			String[] managers = dept.getClientUserId().split(",");
			ClientUser clientUser = null;
			StringBuffer managerNames = new StringBuffer();
			for(String managersId:managers){
				clientUser = clientUserService.getById(managersId);
				if(clientUser!=null){
					managerNames.append(clientUser.getName()+" ");
				}
			}
			if(StringUtils.isNotEmpty(managerNames)){
				dept.setClientUserNames(managerNames.toString());
			}
		}
	}

	@Override
	public ListJson saveDept(Dept dept) {
		ListJson listJson = new ListJson();
		Dept old = getById(dept.getId());	//旧的
		flushManager(dept);
		Dept higher = getById(dept.getDpid());//上级
		
		//上级部门不能是本部门    或   下级部门
		if(old!=null){
			if(dept.getDpid().equals(old.getId())){
				listJson.setMessage("所属部门不能是本部门！");
				listJson.setSuccess(false);
				return listJson;
			}else{
				//是否为下级部门
				if(isSonDept(old.getId(), higher.getId())){
					listJson.setMessage("所属部门不能是下级部门！");
					listJson.setSuccess(false);
					return listJson;
				}
			}
		}
		
		try{
			if(dept!=null){
				
				if(StringUtils.isNotEmpty(dept.getId())){
					//新增
						if(StringUtils.isEmpty(dept.getId())){
							//生成部门编码
							randomCode(dept);
						}
						save(dept);
						
						if(higher!=null){
							changeSonDeptLevel(dept, higher.getLevel()+1);
						}
				}else{
					save(dept);
					//如果是新增同时修改用户的部门信息
					if(StringUtils.isNotEmpty(dept.getClientUserId())){
						String[] managers = dept.getClientUserId().split(",");
						ClientUser clientUser = null;
						for(String id:managers){
							clientUser = clientUserService.getById(id);
							if(clientUser!=null){
								clientUser.setDeptId(dept.getId());
								clientUserService.save(clientUser);
							}
						}
					}
				}
				if(dept.getLevel()==1){
					listJson.setEntity(dept);
				}else{
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("level", dept.getLevel());
					map.put("parentId", dept.getDpid());
					map.put("parentName", higher.getName());
					map.put("parentRemarks", higher.getRemarks());
					map.put("sonId", dept.getId());
					map.put("sonName", dept.getName());
					map.put("sonRemarks", dept.getRemarks());
					map.put("sonManagers", dept.getClientUserNames());
					map.put("sonManagerIds", dept.getClientUserId());
				}
				
				listJson.setMessage("部门编辑成功！");
				listJson.setSuccess(true);	
			}else{
				listJson.setMessage("数据异常！");
				listJson.setSuccess(false);
			}
		}catch(Exception e){
			
		}
		return listJson;
	}

	@Override
	public void changeSonDeptLevel(Dept dept, Integer difference) {
		List<Dept> list = sonDeptList(dept.getId());
		if(list.size()>0){
			for(Dept d:list){
				d.setLevel(dept.getLevel()+1);
				save(d);
				changeSonDeptLevel(d,dept.getLevel()+1);
			}
		}
	}

	@Override
	public List<Dept> sonDeptList(String deptId) {
		DeptQuery query = new DeptQuery();
		query.setDpid(deptId);
		query.setDeleted(false);
		return getEntities(query);
	}

	@Override
	public boolean isSonDept(String ownDeptId, String higherDeptId) {
		Dept dept = getById(higherDeptId);
		Integer level = dept.getLevel();
		do{
			//查询上级
			if(dept.getId().equals(ownDeptId)){	
				return true;
			}
			dept = getById(dept.getDpid());
			level--;
			
		}while(level>1);
		
		return false;
	}
}
