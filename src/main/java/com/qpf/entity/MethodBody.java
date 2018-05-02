package com.qpf.entity;

public class MethodBody {
	private int indent;
	private StringBuffer body;
	private String newLine;
	public MethodBody(){
		indent = 1;
		newLine = System.lineSeparator();
		body = new StringBuffer("{" + newLine(++indent));
	}
	public String getNewLine() {
		return newLine;
	}
	public void setNewLine(String newLine) {
		this.newLine = newLine;
	}
	public StringBuffer getBody() {
		return body;
	}
	public String newLine(int indent){
		String buf = newLine;
		for(int i = 0; i < indent; i++){
			buf += "\t";
		}
		return buf;
	}
	public void append(String content){
		if(content.trim().startsWith("}")){
//			body.append(newLine(--indent) + content);
//			body.replace(body.length() - 1, body.length(), "}");
			body.replace(body.lastIndexOf(newLine), body.length(), "");
			content = newLine(--indent) + content + newLine(indent);
		}
		if(content.trim().endsWith("{")){
			if(content.contains(newLine)){
				content = content.substring(0, content.lastIndexOf(newLine));
			}
			content += newLine(++indent);
		}
		if(content.trim().endsWith(";")){
			content += newLine(indent);
		}
		body.append(content);
	}
	@Override
	public String toString() {
		return body.toString();
	}
}
