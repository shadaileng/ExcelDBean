package com.qpf.entity;

public class Field {
	private String modify;
	private String type;
	private String fieldName;
	private String init;
	private String result;
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
		result = modify + " " + type + " " + fieldName;
		if(init != null && !"".equals(init)){
			result += " = " + init;
		}
		result += ";";
		return result;
	}
	@Override
	public String toString() {
		return "Field [modify=" + modify + ", type=" + type + ", fieldName=" + fieldName + ", init=" + init
				+ ", result=" + result + "]";
	}
}
