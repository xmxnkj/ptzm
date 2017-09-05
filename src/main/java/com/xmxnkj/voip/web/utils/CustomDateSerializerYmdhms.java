/**   
* @Title: CustomDateSerializerYmdhms.java
* @Package com.hsit.utils
* @Description: TODO
* @author XUJC 
* @date 2016年8月29日 下午2:39:08
* @version V1.0   
*/


package com.xmxnkj.voip.web.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @ProjectName:sizt-coupons
 * @ClassName: CustomDateSerializerYmdhms
 * @Description:JACKJSON 日期辅助工具类
 * @author XUJC
 * @date 2016年8月29日 下午2:39:08
 * @UpdateUser:
 * @UpdateDate:   
 * @UpdateRemark:
 * @Copyright: 2016 厦门晟中信息.
 * @versions:1.0
 */

public class CustomDateSerializerYmdhms extends JsonSerializer<Date> {
	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = formatter.format(value);
		jgen.writeString(formattedDate);
		
	}
}
