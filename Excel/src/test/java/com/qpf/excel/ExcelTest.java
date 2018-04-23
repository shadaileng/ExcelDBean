package com.qpf.excel;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;
import org.junit.Test;

public class ExcelTest {
	Logger logger = Logger.getLogger(getClass());
	@Test
	public void getAllTablesTest() throws Exception{
		URL resource = getClass().getClassLoader().getResource("ExcelDBean.xls");
		ExcelHelper.getAllTable(new File(resource.getFile()));
		logger.info("end");
	}
	
}
