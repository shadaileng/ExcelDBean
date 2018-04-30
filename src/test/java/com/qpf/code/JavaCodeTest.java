package com.qpf.code;

import org.junit.Test;

import com.qpf.entity.Field;
import com.qpf.entity.Method;

public class JavaCodeTest {
	@Test
	public void newTest(){
		JavaCode javaCode = new JavaCode("com.qpf.domain.User", false, "用户");
		javaCode.addFields("private", "String", "name", null);
		System.out.println(javaCode);
		javaCode.addAnnotation("org.apache.ibatis.annotations.SelectProvider(type = com.qpf.model.db.UserProvider.class, method = \"selectByFullSql\")");
		Method method = new Method("public", "void", "test", null);
		method.addParams(new Field("", "java.lang.String", "testName", ""));
		method.setBody("{}");
		method.addAnnotations("org.apache.ibatis.annotations.SelectProvider(type = com.qpf.model.db.UserProvider.class, method = \"selectByFullSql\")");
		javaCode.addmethod(method.getName(), method);
		String res = javaCode.build();
		System.out.println(res);
		System.out.println(System.getProperty("user.dir"));
		javaCode.build(System.getProperty("user.dir"));
	}
	
	@Test
	public void typeConvertionTest() {
		String dest = JavaCodeHelper.typeConvertion("list<map<int, int>>");
		System.out.println(dest);
		dest = JavaCodeHelper.simpleType(dest);
		System.out.println(dest);
	}
}
