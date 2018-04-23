package com.qpf.excel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.qpf.entity.Column;
import com.qpf.entity.Table;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelHelper {
	private static Logger logger = Logger.getLogger(ExcelHelper.class);
	public static Map<String, Table> getAllTable(File file) throws Exception{
		Map<String, Table> tables = new HashMap<String, Table>();
		logger.info("load file: " + file.getName());
		Workbook wb = Workbook.getWorkbook(file);
		Sheet[] sheets = wb.getSheets();
		Sheet firstSheet = sheets[0];
		for(int i = 1, l = firstSheet.getRows(); i < l; i++){
			Table table = new Table($(firstSheet.getRow(i), 1), $(firstSheet.getRow(i), 2), $(firstSheet.getRow(i), 3), $(firstSheet.getRow(i), 4));
			logger.info(table.toString());
			tables.put($(firstSheet.getRow(i), 2), table);
		}
		
		for(int i = 1, l = sheets.length; i < l; i++){
			Sheet sheet_tmp = sheets[i];
			String tableName = $(sheet_tmp.getRow(1), 0);
			logger.info(tableName);
			Map<String, Column> columns = new HashMap<String, Column>();
			for(int j = 2, k = sheet_tmp.getRows(); j < k; j ++) {
				String columnName = $(sheet_tmp.getRow(j), 1);
				Column column = new Column(tableName, columnName , $(sheet_tmp.getRow(j), 2), $(sheet_tmp.getRow(j), 3), $(sheet_tmp.getRow(j), 4), $(sheet_tmp.getRow(j), 5), $(sheet_tmp.getRow(j), 6));
				columns.put(columnName, column);
			}
			tables.get(tableName).setColumns(columns);
		}
		logger.info(tables);
		
		return tables;
	}
	
	private static String $(Cell[] row, int index){
		if(index >= row.length) return "";
		if(row[index] == null) return "";
		return row[index].getContents();
	}
}
