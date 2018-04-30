package com.qpf.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qpf.code.JavaCodeHelper;

public class Method {
	private String define;
	private String body;
	private String modify;
	private String type;
	private String name;
	private Map<String, Field> params;
	private List<String> annotations;
	private List<String> imports;
	public Method() {
		define = "";
		body = "";
		modify = "";
		type = "";
		name = "";
		params = new LinkedHashMap<String, Field>();
		annotations = new ArrayList<String>();
		imports = new ArrayList<String>();
	}
	public Method(String modify, String type, String name, Map<String, Field> params) {
		this();
		this.modify = modify;
		this.type = type;
		this.name = name;
		this.params = (params == null) ? new LinkedHashMap<String, Field>() : params;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Field> getParams() {
		return params;
	}
	public void addParams(Field params) {
		this.params.put(params.getFieldName(), params);
	}
	public String getDefine() {
		define += modify + " ";
		
		if(!"".equals(type)) {
			define += type + " ";
		}
		
		define += name + " (";
		
		String buf = "";
		for(Field field : params.values()) {
			buf += JavaCodeHelper.simpleType(field.getType()) + " " + field.getFieldName() + ", ";
		}
		if(!"".equals(buf)) {
			define += buf.substring(0, buf.lastIndexOf(","));
		}
		
		define += ")";
		return define;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getBody() {
		return body;
	}
	public List<String> getAnnotations() {
		return annotations;
	}
	public void addAnnotations(String annotation) {
		
		if(annotation != null && !"".equals(annotation)){
			String buf = "";
			if(annotation.contains("(")) {
				int begin = annotation.indexOf("(");
				int end = annotation.lastIndexOf(")");
				String fullAnnotation = annotation.substring(0, begin);
				imports.add(fullAnnotation);
				buf += JavaCodeHelper.simpleType(fullAnnotation) + "(";
				String tmp = annotation.substring(begin + 1, end);
				while(begin > 0) {
					begin = tmp.indexOf(".class");
					if(begin > 0) {
						int begin_ = tmp.substring(0, begin).lastIndexOf("=");
						imports.add(tmp.substring(begin_ + 1, begin).trim());
						buf += tmp.substring(0, begin_ + 1) + " " + JavaCodeHelper.simpleType(tmp.substring(begin_ + 1, begin)) + ".class";
						tmp = tmp.substring(begin + 6);
						begin = end;
					}else {
						buf += tmp;
					}
				}
				buf += ")";
			}else {
				imports.add(annotation);
			}
			annotations.add(buf);
		}
	}
	public List<String> getImports() {
		return imports;
	}
}
