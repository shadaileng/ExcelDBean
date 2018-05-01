package com.qpf.code;

import com.qpf.entity.Column;
import com.qpf.entity.Field;
import com.qpf.entity.Method;
import com.qpf.entity.Table;

public class JavaCodeHelper {
	/**
	 * 根据table生成java文件
	 * @param table
	 * @param path
	 */
	public static void buildJavaCodeByTable(Table table, String path) {
		String name = table.getTableName();
		String packageName = table.getPackageName();
		String desc = table.getTableDesc();
		/**
		 * 实体类
		 */
		JavaCode code = new JavaCode(packageName + "." + name, false, desc);
		for(Column column : table.getColumns().values()) {
			code.addFields("private", typeConvertion(column.getColumnType()), column.getColumnName(), null);
		}
		code.addConstructor();
		code.addGetterAndSetter();
		code.build(path);
		
		/**
		 * 实体类对应的Mapper
		 */
		// selectByFullSql
		code = new JavaCode(packageName + "." + name + "Mapper", true, desc + "Mapper");
		Method method = new Method("public", "java.util.List<java.util.Map<String, Object>>", "select" + JavaCode.firstUpper(name)  + "ByFullSql", null);
		Field field = new Field("", "String", "fullSQL", "");
		field.setAnnotation("org.apache.ibatis.annotations.Param(\"fullSQL\")");
		method.addParams(field);
		method.setBody("{}");
		method.addAnnotations("org.apache.ibatis.annotations.SelectProvider(type = " + packageName + "." + JavaCode.firstUpper(name) + "Provider.class, method = \"selectByFullSql\")");
		code.addmethod(method.getName(), method);

		// selectUserById
		method = new Method("public", packageName + "." + JavaCode.firstUpper(name), "select" + JavaCode.firstUpper(name)  + "ById", null);
		field = new Field("", packageName + "." + JavaCode.firstUpper(name), name, "");
		field.setAnnotation("org.apache.ibatis.annotations.Param(\"" + name + "\")");
		method.addParams(field);
		method.setBody("{}");
		method.addAnnotations("org.apache.ibatis.annotations.SelectProvider(type = " + packageName + "." + JavaCode.firstUpper(name) + "Provider.class, method = \"selectUserById\")");
		code.addmethod(method.getName(), method);
		
		// selectUserByBean
		method = new Method("public", packageName + "." + JavaCode.firstUpper(name), "select" + JavaCode.firstUpper(name)  + "ByBean", null);
		field = new Field("", packageName + "." + JavaCode.firstUpper(name), name, "");
		field.setAnnotation("org.apache.ibatis.annotations.Param(\"" + name + "\")");
		method.addParams(field);
		method.setBody("{}");
		method.addAnnotations("org.apache.ibatis.annotations.SelectProvider(type = " + packageName + "." + JavaCode.firstUpper(name) + "Provider.class, method = \"selectUserByBean\")");
		code.addmethod(method.getName(), method);
		
		// selectUserByConditions
		method = new Method("public", packageName + "." + JavaCode.firstUpper(name), "select" + JavaCode.firstUpper(name)  + "ByConditions", null);
		field = new Field("", "java.lang.String", "paramWhere", "");
		field.setAnnotation("org.apache.ibatis.annotations.Param(\"paramWhere\")");
		method.addParams(field);
		field = new Field("", "java.lang.String", "... paramOrder", "");
		field.setAnnotation("org.apache.ibatis.annotations.Param(\"paramOrder\")");
		method.addParams(field);
		method.setBody("{}");
		method.addAnnotations("org.apache.ibatis.annotations.SelectProvider(type = " + packageName + "." + JavaCode.firstUpper(name) + "Provider.class, method = \"selectUserByConditions\")");
		code.addmethod(method.getName(), method);

		// updateUserById
		method = new Method("public", packageName + "." + JavaCode.firstUpper(name), "update" + JavaCode.firstUpper(name)  + "ById", null);
		field = new Field("", packageName + "." + JavaCode.firstUpper(name), name, "");
		field.setAnnotation("org.apache.ibatis.annotations.Param(\"" + name + "\")");
		method.addParams(field);
		method.setBody("{}");
		method.addAnnotations("org.apache.ibatis.annotations.UpdateProvider(type = " + packageName + "." + JavaCode.firstUpper(name) + "Provider.class, method = \"updateUserById\")");
		code.addmethod(method.getName(), method);
		
		// deleteUserById
		method = new Method("public", packageName + "." + JavaCode.firstUpper(name), "delete" + JavaCode.firstUpper(name)  + "ById", null);
		field = new Field("", packageName + "." + JavaCode.firstUpper(name), name, "");
		field.setAnnotation("org.apache.ibatis.annotations.Param(\"" + name + "\")");
		method.addParams(field);
		method.setBody("{}");
		method.addAnnotations("org.apache.ibatis.annotations.DeleteProvider(type = " + packageName + "." + JavaCode.firstUpper(name) + "Provider.class, method = \"deleteUserById\")");
		code.addmethod(method.getName(), method);
		
		// insertUser
		method = new Method("public", packageName + "." + JavaCode.firstUpper(name), "insert" + JavaCode.firstUpper(name), null);
		field = new Field("", packageName + "." + JavaCode.firstUpper(name), name, "");
		field.setAnnotation("org.apache.ibatis.annotations.Param(\"" + name + "\")");
		method.addParams(field);
		method.setBody("{}");
		method.addAnnotations("org.apache.ibatis.annotations.DeleteProvider(type = " + packageName + "." + JavaCode.firstUpper(name) + "Provider.class, method = \"insertUser\")");
		code.addmethod(method.getName(), method);
		
		code.build(path);
		
		/**
		 * Provider
		 */
		code = new JavaCode(packageName + "." + name + "Provider", false, desc + "Provider");
		code.addFields("private", "java.lang.String", "realTableName", null);
		code.addFields("private", "java.util.List<java.lang.String>", "columns", "new java.util.ArrayList<java.lang.String>()");
		method = new Method("public", "", code.getClassName(), null);
		int indent = 1;
		StringBuffer buf = new StringBuffer("{" + newLine(++indent, System.lineSeparator()));
		buf.append("realTableName = '" + name + "';" + newLine(indent, System.lineSeparator()));
		code.importGenericity("java.lang.reflect.Field");
		buf.append("Field[] fields = " + packageName + "." + JavaCode.firstUpper(name) + ".class.getDeclaredFields();" + JavaCode.firstUpper(name) + ";" + newLine(indent, System.lineSeparator()));
		buf.append("for (Field field : fields){" + newLine(++indent, System.lineSeparator()));
		buf.append("columns.add(field.getName().toUpperCase());" + newLine(--indent, System.lineSeparator()));
		buf.append("}" + newLine(--indent, System.lineSeparator()));
		buf.append(newLine(indent, System.lineSeparator()) + "}");
		method.setBody(buf.toString());
		code.addmethod(method.getName(), method);
		
		method = new Method("public", "java.lang.String", "selectByFullSql", null);
		field = new Field("", "java.util.Map<java.lang.String, java.lang.String>", "map", null);
		method.addParams(field);
		indent = 1;
		buf = new StringBuffer("{" + newLine(++indent, System.lineSeparator()));
		buf.append("String sql = map.get(\"fullSQL\");" + newLine(indent, System.lineSeparator()));
		buf.append("return sql;" + newLine(--indent, System.lineSeparator()));
		buf.append("}" + newLine(--indent, System.lineSeparator()));
		method.setBody(buf.toString());
		code.addmethod(method.getName(), method);
		code.build(path);
	}
	public static String newLine(int indent, String NEWLINE){
		String newline = NEWLINE;
		for(int i = 0; i < indent; i++){
			newline += "\t";
		}
		return newline;
	}
	/**
	 * 类型匹配
	 * @param src
	 * @return
	 */
	public static String typeConvertion(String src) {
		String dest = "";
		
		if(src != null) {
			src = src.toLowerCase().trim();
			if(src.contains("<")) {
				int begin = src.indexOf("<");
				int end = src.lastIndexOf(">");
				if(begin < end) {
					if("list".equals(src.substring(0, begin))) {
						dest += "java.util.List<";
						src = src.substring(begin + 1, end);
						dest += typeConvertion(src);
						dest += ">";
					} else if("map".equals(src.substring(0, begin))) {
						dest += "java.util.Map<";
						dest += typeConvertion(src.substring(begin + 1, src.indexOf(",")));
						dest += ", ";
						dest += typeConvertion(src.substring(src.indexOf(",") + 1, end));
						dest += ">";
					}
				}
			}else if("int".equals(src)) {
				dest += "java.lang.Integer";
			}else if("string".equals(src)) {
				dest += "java.lang.String";
			}else if("double".equals(src)) {
				dest += "java.lang.Double";
			}else if("date".equals(src)) {
				dest += "java.util.Date";
			}
		}
		
		return dest;
	}
	
	/**
	 * 全类名解析
	 * @param type
	 * @return
	 */
	public static String simpleType(String type) {
		String res = "";
		
		if(type != null && !"".equals(type)) {
			if(type.toLowerCase().trim().contains("<")) {
				int begin = type.indexOf("<");
				int end = type.lastIndexOf(">");
				if(begin < end) {
					String tmp = type.substring(0, begin);
					if(tmp.toLowerCase().contains("list")) {
						res += tmp.substring(tmp.lastIndexOf(".") + 1, begin) + "<";
						type = type.substring(begin + 1, end);
						res += simpleType(type);
						res += ">";
					}else if(tmp.toLowerCase().contains("map")) {
						res += tmp.substring(tmp.lastIndexOf(".") + 1, begin) + "<";
						res += simpleType(type.substring(begin + 1, type.indexOf(",")));
						res += ", ";
						res += simpleType(type.substring(type.indexOf(",") + 1, end));
						res += ">";
					}
				}
			}else {
				res += type.substring(type.lastIndexOf(".") + 1);
			}
		}
		return res;
	}
}
