package com.qpf.excel;

import java.io.File;
import java.net.URL;

import org.junit.Test;

public class ExcelTest {
	
	@Test
	public void getAllTablesTest() throws Exception{
		URL resource = getClass().getClassLoader().getResource("ExcelDBean.xls");
		ExcelHelper.getAllTable(new File(resource.getFile()));
	}
	
}
