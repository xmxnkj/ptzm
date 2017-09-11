package com.xmxnkj.voip.ivr.entity;

import java.util.List;

import com.xmxnkj.voip.common.entity.VoipEntity;

/**	应答
 * @ProjectName:voip
 * @ClassName: AiResponse
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class AiResponse extends VoipEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2400546824992177351L;
	//如果keywords为空，表示为默认流程
	private List<String> keywords;
	//下一步代码优先
	private String nextStepCode;
	private String answer;
	private String sound;
	private Boolean remainOriginal;	//是否为原
	private String remainOriginalAnswer;
	private String remainOriginalSound;
	private String code;			//代码
	private Boolean repeat;		//是否重复
	private String flowId;
	private String keywordsString;
	private String templateId;
	private String flowCode;
	private Boolean blockAsr;
	
	
	public Boolean getBlockAsr() {
		return blockAsr;
	}
	public void setBlockAsr(Boolean blockAsr) {
		this.blockAsr = blockAsr;
	}
	public String getFlowCode() {
		return flowCode;
	}
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	public String getNextStepCode() {
		return nextStepCode;
	}
	public void setNextStepCode(String nextStepCode) {
		this.nextStepCode = nextStepCode;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public Boolean getRemainOriginal() {
		return remainOriginal;
	}
	public void setRemainOriginal(Boolean remainOriginal) {
		this.remainOriginal = remainOriginal;
	}
	public String getRemainOriginalAnswer() {
		return remainOriginalAnswer;
	}
	public void setRemainOriginalAnswer(String remainOriginalAnswer) {
		this.remainOriginalAnswer = remainOriginalAnswer;
	}
	public String getRemainOriginalSound() {
		return remainOriginalSound;
	}
	public void setRemainOriginalSound(String remainOriginalSound) {
		this.remainOriginalSound = remainOriginalSound;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getRepeat() {
		return repeat!=null?repeat:false;
	}
	public void setRepeat(Boolean repeat) {
		this.repeat = repeat;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getKeywordsString() {
		return keywordsString;
	}
	public void setKeywordsString(String keywordsString) {
		this.keywordsString = keywordsString;
	}
}