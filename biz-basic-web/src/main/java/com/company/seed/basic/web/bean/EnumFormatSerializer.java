package com.company.seed.basic.web.bean;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 
 * 枚举转换json
 * @date 2015-10-22
 */
public class EnumFormatSerializer implements ObjectSerializer {
	private String property;

	public EnumFormatSerializer(String String) {
		this.property = String;
	}

	public void write(JSONSerializer serializer, Object object,
			Object fieldName, Type fieldType) {
		SerializeWriter out = serializer.getWriter();
		if (object == null) {
			serializer.getWriter().writeNull();
			return;
		}

		Object result = null;
		try {
			Method method = object.getClass().getMethod(
					"get" + property.substring(0, 1).toUpperCase()
							+ property.substring(1));
			result = method.invoke(object);
		} catch (Exception e) {
			result = "";
		}
		if (result == null) {
			result = "";
		}
		out.writeString(result.toString());
	}
	public static SerializeConfig getSerializeConfig(Class<?> changeEnum,String propertyName){
		if(changeEnum==null || propertyName==null){
			return null;
		}
		SerializeConfig config = new SerializeConfig();
		config.put(changeEnum, new EnumFormatSerializer(propertyName));
		return config;
	}
}