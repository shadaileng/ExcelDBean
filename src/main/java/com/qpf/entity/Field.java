package com.qpf.entity;

import com.qpf.code.JavaCodeHelper;

public class Field {
	private String modify;
	private String type;
	private String fieldName;
	private String init;
	private String result;
	private String annotation;
	public Field(String modify, String type, String fieldName, String init) {
		super();
		this.modify = modify;
		this.type = type;
		this.fieldName = fieldName;
		this.init = init;
	}
	public String getModify() {
		return modify;
	}
	public void setModify(String modify) {
		this.modify = modify;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getInit() {
		return init;
	}
	public void setInit(String init) {
		this.init = init;
	}
	public String getResult() {
		result = modify + " " + JavaCodeHelper.simpleType(type) + " " + fieldName;
		if(init != null && !"".equals(init)){
			result += " = new " + JavaCodeHelper.simpleType(init);
		}
		result += ";";
		return result;
	}
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	@Override
	public String toString() {
		return "Field [modify=" + modify + ", type=" + type + ", fieldName=" + fieldName + ", init=" + init
				+ ", result=" + result + "]";
	}
}
