package com.qpf.entity;

import java.util.Map;

public class Table {
	private String id;
	private String packageName;
	private String tableName;
	private String tableName_zhCN;
	private String tableDesc;
	private Map<String, Column> columns;
	public Table(String id, String packageName, String tableName, String tableName_zhCN, String tableDesc) {
		super();
		this.id = id;
		this.packageName = packageName;
		this.tableName = tableName;
		this.tableName_zhCN = tableName_zhCN;
		this.tableDesc = tableDesc;
	}
	public Table(String packageName, String tableName, String tableName_zhCN, String tableDesc) {
		this("", packageName, tableName, tableName_zhCN, tableDesc);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableName_zhCN() {
		return tableName_zhCN;
	}
	public void setTableName_zhCN(String tableName_zhCN) {
		this.tableName_zhCN = tableName_zhCN;
	}
	public String getTableDesc() {
		return tableDesc;
	}
	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}
	
	public Map<String, Column> getColumns() {
		return columns;
	}
	public void setColumns(Map<String, Column> columns) {
		this.columns = columns;
	}
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("Table [id=" + id + ", packageName=" + packageName + ", tableName=" + tableName + ", tableName_zhCN="
				+ tableName_zhCN + ", tableDesc=" + tableDesc + "]\n");
		
		for(Map.Entry<String, Column> entry : columns.entrySet()){
			buf.append("  " + entry.getKey() + ": " + entry.getValue() + "\n");
		}
		
		return buf.toString();
	}
}
