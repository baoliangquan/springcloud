package com.company.seed.basic.web.bean;

import com.alibaba.fastjson.serializer.EnumSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 使用方法
 * config.put(PoJo.class, new JavaBeanSerializer(PoJo.class));
 * 参数一是你的枚举类型，参数二是待输出的值(数组)
 * config.put(YourEnum.class, new EnumCnSerializer(YourEnum.class,"desc"));
 * @param <T>
 */           
@SuppressWarnings("rawtypes")
public class EnumCnSerializer<T extends Enum> extends EnumSerializer{

	private Class<T> clazz;
	private String[] proptertiesName;
	public EnumCnSerializer(Class<T> clazz, String... proptertiesName) {
		super();
		this.clazz = clazz;
		this.proptertiesName = proptertiesName;
	}

	@Override
	public void write(JSONSerializer serializer, Object object,
			Object fieldName, Type fieldType) throws IOException {
		SerializeWriter out = serializer.getWriter();
        if (object == null) {
            serializer.getWriter().writeNull();
            return;
        }
		T e = clazz.cast(object) ;
        serializer.write(e.name());
        if(proptertiesName==null){
        	return;
        }
        for(String propertyName:proptertiesName){
        	propertyName = propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
        	try {
	        	Method method = clazz.getMethod("get" + propertyName);
	        	if(fieldName instanceof Integer){
		        	out.write(",");
		        	serializer.write(method.invoke(e));
		        }else{
			        out.write(",");
			        serializer.write(fieldName + propertyName);
			        out.write(":");
			        serializer.write(method.invoke(e));
		        }
        	} catch (Exception e1) {
    			return;
    		} 
        }
	}
}
