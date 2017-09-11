package com.xmxnkj.voip.voice.dao;

import java.util.List;

import com.hsit.common.dao.Dao;
import com.xmxnkj.voip.voice.entity.VoiceTemplate;
import com.xmxnkj.voip.voice.entity.query.VoiceTemplateQuery;

public interface IVoiceTemplateDao extends Dao<VoiceTemplate, VoiceTemplateQuery> {

	/**
	 * 增加一条数据
	 * @param voice
	 * @return
	 */
	Boolean add(VoiceTemplate voice);
	/**
	 * 根据id删除一条数据
	 * @param id
	 * @return
	 */
	Boolean deleteById(String id);
	/**
	 * 更新一条数据
	 * @param id
	 * @param voice
	 * @return
	 */
	Boolean update(String id,VoiceTemplate voice);
	/**
	 * 获取全部的数据
	 * @return
	 */
	List<VoiceTemplate> find();
	/**
	 * 根据id获取一条具体的数据
	 * @param id
	 * @return
	 */
	VoiceTemplate findById(String id);
}
