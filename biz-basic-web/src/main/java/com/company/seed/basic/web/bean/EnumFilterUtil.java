/**
 * 
 */
package com.company.seed.basic.web.bean;

import java.lang.reflect.Method;

/**
 * 过滤枚举
 * @date 2015-11-4
 */
public class EnumFilterUtil {
	public static String replaceEnum(String json,
			ReplaceEnumBean... replaceEnumBeans) {
		String newJson = json;
		if (replaceEnumBeans != null && replaceEnumBeans.length > 0) {
			try {
				for (int i = 0; i < replaceEnumBeans.length; i++) {
					ReplaceEnumBean bean = replaceEnumBeans[i];
					Class<?> enumClass = bean.getEnumClass();
					String methodStr = bean.getEnumProperty();
					String replaceStr = bean.getReplaceProperty();
					for (Object obj : enumClass.getEnumConstants()) {
						String key = "\"" + replaceStr + "\"" + ":" + "\""
								+ obj + "\"";
						Method method = obj.getClass().getMethod(methodStr);
						String value = "\"" + replaceStr + "\"" + ":" + "\""
								+ method.invoke(obj) + "\"";
						newJson = newJson.replace(key,value);
					}
				}
				
			} catch (Exception e) {
				return json;
			}

		}
		return newJson;
	}
}
