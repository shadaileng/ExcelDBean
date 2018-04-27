package com.qpf.entity;

public class Column {
	private String tableName;
	private String columnName;
	private String columnDesc;
	private String columnLength;
	private String columnType;
	private String decimals;
	private String remark;
	public Column(String tableName, String columnName, String columnDesc, String columnLength, String columnType,
			String decimals, String remark) {
		super();
		this.tableName = tableName;
		this.columnName = columnName;
		this.columnDesc = columnDesc;
		this.columnLength = columnLength;
		this.columnType = columnType;
		this.decimals = decimals;
		this.remark = remark;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnDesc() {
		return columnDesc;
	}
	public void setColumnDesc(String columnDesc) {
		this.columnDesc = columnDesc;
	}
	public String getColumnLength() {
		return columnLength;
	}
	public void setColumnLength(String columnLength) {
		this.columnLength = columnLength;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getDecimals() {
		return decimals;
	}
	public void setDecimals(String decimals) {
		this.decimals = decimals;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Column [tableName=" + tableName + ", columnName=" + columnName + ", columnDesc=" + columnDesc
				+ ", columnLength=" + columnLength + ", columnType=" + columnType + ", decimals=" + decimals
				+ ", remark=" + remark + "]";
	}
}
