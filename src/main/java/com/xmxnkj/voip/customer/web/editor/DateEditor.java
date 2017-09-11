package com.xmxnkj.voip.customer.web.editor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.PropertiesEditor;

import com.hsit.common.utils.DateUtil;

/**
 * 
 * @author zjx
 *
 */
public class DateEditor extends PropertiesEditor {
	/* 
	 * 
	 * @see org.springframework.beans.propertyeditors.PropertiesEditor#setAsText(java.lang.String)
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isEmpty(text)) {
			setValue(null);
		}else{
			if (text.contains(":")) {
				setValue(DateUtil.parseDateTime(text));
			}else{
				setValue(DateUtil.parseDate(text));
			}
		}
	}
}
