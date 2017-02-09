package com.company.seed.basic.web.bean;
/**
 * 
 * 替换枚举
 * @date 2015-11-4
 */
public class ReplaceEnumBean {
	private Class<?> enumClass;
	private String enumProperty;
	private String replaceProperty;

	public Class<?> getEnumClass() {
		return enumClass;
	}

	public void setEnumClass(Class<?> enumClass) {
		this.enumClass = enumClass;
	}

	public String getEnumProperty() {
		return enumProperty == null ? "" : "get"
				+ enumProperty.substring(0, 1).toUpperCase()
				+ enumProperty.substring(1);
	}

	public void setEnumProperty(String enumProperty) {
		this.enumProperty = enumProperty;
	}

	public String getReplaceProperty() {
		return replaceProperty;
	}

	public void setReplaceProperty(String replaceProperty) {
		this.replaceProperty = replaceProperty;
	}

	public ReplaceEnumBean(Class<?> enumClass, String enumProperty,
						   String replaceProperty) {
		this.enumClass = enumClass;
		this.enumProperty = enumProperty;
		this.replaceProperty = replaceProperty;
	}

}
