package com.qpf.code;

import org.junit.Test;

public class JavaCodeTest {
	@Test
	public void newTest(){
		JavaCode javaCode = new JavaCode("com.qpf.domain.User", false, "用户");
		System.out.println(javaCode);
	}
}
