package com.qpf.code;

import java.util.Map;

import com.qpf.entity.Column;
import com.qpf.entity.Table;

public class JavaCodeHelper {
	public static void buildJavaCodeByTable(Table table) {
		String name = table.getTableName();
		String packageName = table.getPackageName();
		String desc = table.getTableDesc();
		JavaCode code = new JavaCode(packageName + "." + name, false, desc);
		for(Map.Entry<String, Column> entry : table.getColumns().entrySet()) {
			Column column = entry.getValue();
			code.addFields("private", column.getColumnType(), column.getColumnName(), null);
		}
		code.build();
	}
}
