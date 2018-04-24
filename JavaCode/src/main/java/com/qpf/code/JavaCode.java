package com.qpf.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.qpf.entity.Field;

public class JavaCode {
	private String packageName;
	private List<String> importPackage;
	private String desc4Class;
	private List<String> annotations;
	private String className;
	private boolean isInterface;
	private List<String> interfaces;
	private String subClassName;
	private List<String> fields;
	private Map<String, String> methods;
	private static Logger logger = Logger.getLogger(JavaCode.class);
	public JavaCode() {
		packageName = "";
		importPackage = new ArrayList<String>();
		desc4Class = "";
		annotations = new ArrayList<String>();
		className = "";
		subClassName = "";
		isInterface = false;
		interfaces = new ArrayList<String>();
		fields = new ArrayList<String>();
		methods = new HashMap<String, String>();
	}
	public JavaCode(String className, boolean isInterface, String desc){
		this();
		String[] package_class = splitFullClassName(className);
		this.packageName = package_class[0];
		this.className = package_class[1];
		this.isInterface = isInterface;
		this.desc4Class = desc;
	}
	
	/**
	 * 根据全类名导入该类型
	 * @param className
	 */
	public void importPackage(String className){
		logger.info("import: " + className);
		if(isNotEmpty(this.packageName)){
			if(className.indexOf("java.lang") == -1 && className.indexOf(this.packageName) == -1 && this.importPackage.indexOf(className) == -1){
				this.importPackage.add(className);
			}
		}else{
			if(className.indexOf("java.lang") == -1 && className.indexOf(".") != -1 && this.importPackage.indexOf(className) == -1){
				this.importPackage.add(className);
			}
		}
	}
	
	public void extendsSubClass(String subClassName){
		logger.info("extends: " + subClassName);
		if(isNotEmpty(subClassName)){
			importPackage(subClassName);
			this.subClassName = splitFullClassName(subClassName)[1];
		}
	}
	
	public void implementInterface(String interfaceName){
		logger.info("implement: " + interfaceName);
		if(isNotEmpty(interfaceName)){
			importPackage(interfaceName);
			interfaces.add(splitFullClassName(interfaceName)[1]);
		}
	}
	

	/**
	 * 定义属性
	 * @param field 属性名
	 * @param type  属性类型
	 * @param init  初始值
	 */
	public void addFields(String modify, String field, String type, String init){
		logger.info("add field: " + type + " " + field + " = " + init);
		if(isNotEmpty(field) && isNotEmpty(type)){
			importPackage(type);
			new Field(modify, type, field, init);
			
			
			StringBuffer buf = new StringBuffer("private ");
			buf.append(splitFullClassName(type)[1] + " ");
			buf.append(field + " ");
			if(isNotEmpty(init)){
				buf.append("= " + init);
			}
			buf.append(";");
			this.fields.add(buf.toString());
		}
	}
	
	/**
	 * 添加函数
	 * @param methodDefine  函数声明
	 * @param body          函数体
	 */
	public void addmethod(String methodDefine, String body){
		this.methods.put(methodDefine, body);
	}
	

	
	
	public void addConstructor(Map<String, String> params){
		String methodDefine = "public " + className + " (";
		for(Map.Entry<String, String> entry : params.entrySet()){
//			entry.getKey() + 
		}
	}
	
	public String[] splitFullClassName(String className){
		if(isNotEmpty(className)){
			int index = className.lastIndexOf(".");
			if(index < 0){
				return new String[]{"", className};
			}
			return new String[]{className.substring(0, index), className.substring(index + 1)};
		}
		return new String[]{"", ""};
	}
	
	public boolean isNotEmpty(String str){
		return str != null && str.length() > 0;
	}
	
	
	@Override
	public String toString() {
		return "JavaCode [packageName=" + packageName + ", importPackage=" + importPackage + ", desc4Class="
				+ desc4Class + ", annotations=" + annotations + ", className=" + className + ", isInterface="
				+ isInterface + ", fields=" + fields + ", methods=" + methods + "]";
	}
	
	
}
