package com.qpf.code;

import java.util.Map;


import com.qpf.entity.Column;
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
		JavaCode code = new JavaCode(packageName + "." + name, false, desc);
		for(Map.Entry<String, Column> entry : table.getColumns().entrySet()) {
			Column column = entry.getValue();
			code.addFields("private", typeConvertion(column.getColumnType()), column.getColumnName(), null);
		}
		code.build(path);
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
