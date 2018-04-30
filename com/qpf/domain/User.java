package com.qpf.domain;
import String;
import org.apache.ibatis.annotations.SelectProvider;
import com.qpf.model.db.UserProvider;
import ;
/**
 * 用户
 **/
@SelectProvider(type = UserProvider.class, method = "selectByFullSql")
public class User {
	private String name;
	@SelectProvider(type = UserProvider.class, method = "selectByFullSql")
	public void test (String testName)public void test (String testName){}
	public User (String name){
		this.name = name;
		
	}
	public String getName (){
		return this.name
	}
	public String setName (String name){
		this.name = name;
	}
	
}