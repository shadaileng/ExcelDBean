package com.qpf.excel;

import java.io.File;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.qpf.code.JavaCodeHelper;
import com.qpf.entity.Table;

public class ExcelTest {
	Logger logger = Logger.getLogger(getClass());
	@Test
	public void getAllTablesTest() throws Exception{
		URL resource = getClass().getClassLoader().getResource("ExcelDBean.xls");
		Map<String, Table> tables = ExcelHelper.getAllTable(new File(resource.getFile()));
		for(Map.Entry<String, Table> entry : tables.entrySet()) {
			Table table = entry.getValue();
			JavaCodeHelper.buildJavaCodeByTable(table);
		}
		
		
		logger.info("end");
	}
	
}
