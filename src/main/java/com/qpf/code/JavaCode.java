package com.qpf.code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.qpf.entity.Field;
import com.qpf.entity.Method;

public class JavaCode {
	private String packageName;
	private List<String> importPackage;
	private String desc4Class;
	private List<String> annotations;
	private String className;
	private boolean isInterface;
	private List<String> interfaces;
	private String subClassName;
	private Map<String, Field> fields;
	private Map<String, Method> methods;
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
		fields = new LinkedHashMap<String, Field>();
		methods = new LinkedHashMap<String, Method>();
		indent = 0;
	}
	public JavaCode(String className, boolean isInterface, String desc){
		this();
		String[] package_class = splitFullClassName(className);
		this.packageName = package_class[0];
		this.className = firstUpper(package_class[1]);
		this.isInterface = isInterface;
		this.desc4Class = desc;
	}
	public String getClassName() {
		return className;
	}
	/**
	 * 根据全类名导入该类型
	 * @param className
	 */
	public void importPackage(String className){
		if(!className.contains(".")) {
			return;
		}
		if(isNotEmpty(this.packageName)){
			if(className.indexOf("java.lang") == -1 && className.indexOf(this.packageName) == -1 && this.importPackage.indexOf(className) == -1){
				logger.info("import: " + className);
				this.importPackage.add(className);
			}
		}else{
			if(className.indexOf("java.lang") == -1 && className.indexOf(".") != -1 && this.importPackage.indexOf(className) == -1){
				logger.info("import: " + className);
				this.importPackage.add(className);
			}
		}
	}
	
	/**
	 * 导入范型
	 * @param type
	 */
	public void importGenericity(String type) {
		if(type.contains("<")) {
			int begin = type.indexOf("<");
			int end = type.lastIndexOf(">");
			String type_ = type.substring(0, begin);
			if(!importPackage.contains(type_)) {
				importPackage(type_);
				importGenericity(type.substring(begin + 1, end));
			}
		}else {
			if(!importPackage.contains(type)) {
				importPackage(type);
			}
		}
	}
	
	/**
	 * 添加注解，注解全类名解析
	 * @param annotation
	 */
	public void addAnnotation(String annotation){
		if(isNotEmpty(annotation)){
			String buf = "";
			if(annotation.contains("(")) {
				int begin = annotation.indexOf("(");
				int end = annotation.lastIndexOf(")");
				String fullAnnotation = annotation.substring(0, begin);
				importPackage(fullAnnotation);
				buf += JavaCodeHelper.simpleType(fullAnnotation) + "(";
				String tmp = annotation.substring(begin + 1, end);
				while(begin > 0) {
					begin = tmp.indexOf(".class");
					if(begin > 0) {
						int begin_ = tmp.substring(0, begin).lastIndexOf("=");
						importPackage(tmp.substring(begin_ + 1, begin).trim());
						buf += tmp.substring(0, begin_ + 1) + " " + JavaCodeHelper.simpleType(tmp.substring(begin_ + 1, begin)) + ".class";
						tmp = tmp.substring(begin + 6);
						begin = end;
					}else {
						buf += tmp;
					}
				}
				buf += ")";
			}else {
				importPackage(annotation);
			}
			annotations.add(buf);
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
			importGenericity(type);
			if(init != null && init.indexOf(" ") > 0 && init.indexOf("(") > 0) {
				importGenericity(init.substring(init.indexOf(" ") + 1, init.indexOf("(")));
			}

			Field field = new Field(modify, type, fieldName, init);
			String result = field.getResult();
			logger.info("field: " + result);
			this.fields.put(field.getFieldName(), field);
		}
	}
	
	/**
	 * 添加函数
	 * @param methodDefine  函数声明
	 * @param body          函数体
	 */
	public void addmethod(String name, Method method){
		if(!"void".equals(method.getType()) && !className.equals(method.getType())) {
			importGenericity(method.getType());
		}

		Map<String, Field> paramList = method.getParams();
		if(paramList != null &&  paramList.size() > 0) {
			for(Field field : paramList.values()) {
				importGenericity(field.getType());
			}
		}
		this.methods.put(name, method);
	}
	
	/**
	 * 添加构造函数
	 */
	public void addConstructor(){
		Method method = new Method("public", "", className, null);
		method.getMethodBody().append("super();");
		addmethod(className, method);
		
		method = new Method("public", "", className, fields);
		for(Field field : fields.values()){
			method.getMethodBody().append("this." + field.getFieldName() + " = " + field.getFieldName() + ";" + newLine(0));
		}
		addmethod(className, method);
	}
	
	public void addConstructor(Method method) {
		if(!className.equals(method.getName())){
			return ;
		}
		addmethod(className, method);
	}
	
	/**
	 * 添加getter和setter
	 */
	public void addGetterAndSetter(){
		for(Field field : fields.values()){
			String name = field.getFieldName();
			String type = JavaCodeHelper.simpleType(field.getType());
			if(name.indexOf("is") == 0){
				Method method = new Method("public", type, name, null);
				method.getMethodBody().append("return this." + name + ";");
				addmethod(name, method);
				Map<String, Field> fieldTmp = new LinkedHashMap<String, Field>();
				fieldTmp.put(name, new Field("", type, name, ""));
				method = new Method("public", type, "set" + name.substring(2), fieldTmp);
				method.getMethodBody().append("this." + name + " = " + name + ";");
				addmethod(method.getName(), method);
			}else{
				Method method = new Method("public", type, "get" + firstUpper(name), null);
				method.getMethodBody().append("return this." + name + ";");
				addmethod(method.getName(), method);
				Map<String, Field> fieldTmp = new LinkedHashMap<String, Field>();
				fieldTmp.put(name, new Field("", type, name, ""));
				
				method = new Method("public", type, "set" + firstUpper(name), fieldTmp);
				method.getMethodBody().append("this." + name + " = " + name + ";");
				addmethod(method.getName(), method);
			}
		}
	}
	
	/**
	 * 构建实体类文本
	 */
	public String build(){
		StringBuffer buf = new StringBuffer();
		// 包名
		if(isNotEmpty(this.packageName)){
			buf.append("package " + packageName + ";" + newLine(0));
		}
		// 导入包
		/**
		 * 导入包之前遍历函数，添加函数需要的包
		 */
		for(Method method : methods.values()){
			List<String> packages = method.getImports();
			for(String imports : packages) {
				importGenericity(imports);
			}
		}
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
		for(Field field : fields.values()){
			buf.append(field.getResult() + newLine(0));
		}
		
		// 构造函数
//		if(!isInterface){
//			addConstructor();
//		}
		
		//getter & setter
//		addGetterAndSetter();
		
		for(Method method : methods.values()){
			// 函数注解
			List<String> annotation = method.getAnnotations();
			for(String str : annotation){
				buf.append("@" + str + newLine(0));
			}
			if(!isInterface){
				buf.append(method.getDefine() + method.getBody());
			}else {
				buf.append(method.getDefine() + ";");
			}
			buf.append(newLine(0));
		}
		
		buf.append(newLine(-1) + "}");
		
		return buf.toString();
	}
	public void build(String path) {
		File dir = new File(path + "/" + packageName.replace(".", "/"));
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, className + ".java");
		try {
			if(!file.exists()) {
					file.createNewFile();
			}
			logger.info("file: " + file.getPath());
			String charset = "utf-8";
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset ));
			String buf = build();
			bw.write(buf);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public String newLine(int flag){
		indent += flag;
		String newline = NEWLINE;
		for(int i = 0; i < indent; i++){
			newline += "\t";
		}
		return newline;
	}
	
	public static String firstUpper(String fieldName){
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
