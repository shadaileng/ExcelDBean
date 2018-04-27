package com.qpf.code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
	private List<Field> fields;
	private Map<String, String> methods;
	private static Logger logger = Logger.getLogger(JavaCode.class);
	private int indent;
//	private final String NEWLINE = "\n";
	private final String NEWLINE = System.lineSeparator();
	public JavaCode() {
		packageName = "";
		importPackage = new ArrayList<String>();
		desc4Class = "";
		annotations = new ArrayList<String>();
		className = "";
		subClassName = "";
		isInterface = false;
		interfaces = new ArrayList<String>();
		fields = new ArrayList<Field>();
		methods = new LinkedHashMap<String, String>();
		indent = 0;
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
	
	/**
	 * 添加注解
	 * @param annotation
	 */
	public void addAnnotation(String annotation){
		if(isNotEmpty(annotation)){
			importPackage(annotation);
			annotations.add(annotation);
		}
	}
	
	/**
	 * 继承父类
	 * @param subClassName
	 */
	public void extendsSubClass(String subClassName){
		logger.info("extends: " + subClassName);
		if(isNotEmpty(subClassName)){
			importPackage(subClassName);
			this.subClassName = splitFullClassName(subClassName)[1];
		}
	}
	
	/**
	 * 实现接口
	 * @param interfaceName
	 */
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
	public void addFields(String modify, String type, String fieldName, String init){
		logger.info("add field: " + type + " " + fieldName + " = " + init);
		if(isNotEmpty(fieldName) && isNotEmpty(type)){
			importPackage(type);
			Field field = new Field(modify, type, fieldName, init);
			String result = field.getResult();
			logger.info("field: " + result);
			this.fields.add(field);
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
	
	/**
	 * 添加构造函数
	 */
	public void addConstructor(){
		String methodDefine = "public " + className + " ()";
		String methodBody = "{" + newLine(1) + "super();" + newLine(-1) + "}" + newLine(0);
		addmethod(methodDefine, methodBody);
		
		methodDefine = "public " + className + " (";
		StringBuffer buf = new StringBuffer();
		for(Field field : fields){
			buf.append(field.getType() + " " + field.getFieldName() + ", ");
		}
		methodDefine += buf.toString();
		methodDefine = methodDefine.substring(0, methodDefine.lastIndexOf(","));
		methodDefine += ")";
		methodBody = "{" + newLine(1);
		buf = new StringBuffer();
		for(Field field : fields){
			buf.append("this." + field.getFieldName() + " = " + field.getFieldName() + ";" + newLine(0));
		}
		methodBody += buf.toString() + newLine(-1) + "}" + newLine(0);
		addmethod(methodDefine, methodBody);
	}
	
	/**
	 * 添加getter和setter
	 */
	public void addGetterAndSetter(){
		for(Field field : fields){
			String name = field.getFieldName();
			String type = field.getType();
			String methodDefine = "";
			String methodBody = "";
			if(name.indexOf("is") == 0){
				methodDefine = "public " + type + name + " ()";
				methodBody = "{" + newLine(1) + "return this." + name + newLine(-1) + "}" + newLine(0);
				addmethod(methodDefine, methodBody);
				methodDefine = "public " + type + " set" + name.substring(2) + " (" + type + " " + name + ")";
				methodBody = "{" + newLine(1) + "this." + name + " = " + name + ";" + newLine(-1) + "}" + newLine(0);
			}else{
				methodDefine = "public " + type + " get" + firstUpper(name) + " ()";
				methodBody = "{" + newLine(1) + "return this." + name + newLine(-1) + "}" + newLine(0);
				addmethod(methodDefine, methodBody);
				methodDefine = "public " + type + " set" + firstUpper(name) + " (" + type + " " + name + ")";
				methodBody = "{" + newLine(1) + "this." + name + " = " + name + ";" + newLine(-1) + "}" + newLine(0);
			}
			if(!"".equals(methodDefine)){
				methods.put(methodDefine, methodBody);
			}
			
		}
	}
	
	/**
	 * 构建实体类文本
	 */
	public void build(){
		StringBuffer buf = new StringBuffer();
		// 包名
		if(isNotEmpty(this.packageName)){
			buf.append("package " + packageName + ";" + newLine(0));
		}
		// 导入包
		for(String packages : importPackage){
			buf.append("import " + packages + ";" + newLine(0));
		}
		// 类注释
		buf.append("/**" + newLine(0) + " * " + desc4Class + newLine(0) + " **/" + newLine(0));
		
		// 类注解
		for(String str : annotations){
			buf.append("@" + str + newLine(0));
		}
		
		// 类声明
		if(!isInterface){
			buf.append("public class " + className);
		}else{
			buf.append("public interface " + className);
		}
		// 继承
		if(!"".equals(subClassName)){
			buf.append(" extends " + subClassName);
		}
		// 接口
		if(interfaces != null && interfaces.size() > 0){
			buf.append(" implement ");
			String tmp = "";
			for(String str : interfaces){
				tmp += str + ", ";
			}
			tmp.substring(0, tmp.lastIndexOf(","));
			buf.append(tmp);
		}
		buf.append(" {" + newLine(1));
		
		// 定义属性
		for(Field field : fields){
			buf.append(field.getResult() + newLine(0));
		}
		
		// 构造函数
		addConstructor();
		
		//getter & setter
		addGetterAndSetter();
		
		for(Map.Entry<String, String> entry : methods.entrySet()){
			buf.append(entry.getKey() + entry.getValue());
		}
		
		
		
		buf.append(newLine(-1) + "}");
		
		System.out.println(buf.toString());
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
	
	public String newLine(int flag){
		indent += flag;
		String newline = NEWLINE;
		for(int i = 0; i < indent; i++){
			newline += "\t";
		}
//		System.out.println(indent);
		return newline;
	}
	
	public String firstUpper(String fieldName){
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
	@Override
	public String toString() {
		return "JavaCode [packageName=" + packageName + ", importPackage=" + importPackage + ", desc4Class="
				+ desc4Class + ", annotations=" + annotations + ", className=" + className + ", isInterface="
				+ isInterface + ", interfaces=" + interfaces + ", subClassName=" + subClassName + ", fields=" + fields
				+ ", methods=" + methods + ", indent=" + indent + "]";
	}
	
}
