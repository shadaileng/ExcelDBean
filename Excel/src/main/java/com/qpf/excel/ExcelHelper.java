package com.qpf.excel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.qpf.entity.Table;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelHelper {
	public static Map<String, Table> getAllTable(File file) throws Exception{
		Map<String, Table> tables = new HashMap<String, Table>();
		
		Workbook wb = Workbook.getWorkbook(file);
		Sheet[] sheets = wb.getSheets();
		Sheet firstSheet = sheets[0];
		for(int i = 1, l = firstSheet.getRows(); i < l; i++){
			Table table = new Table($(firstSheet.getRow(i), 1), $(firstSheet.getRow(i), 2), $(firstSheet.getRow(i), 3), $(firstSheet.getRow(i), 4));
			System.out.println(table.toString());
		}
		
		for(int i = 1, l = sheets.length; i < l; i++){
			
		}
		
		
		return tables;
	}
	
	private static String $(Cell[] row, int index){
		if(index >= row.length) return "";
		if(row[index] == null) return "";
		return row[index].getContents();
	}
}
