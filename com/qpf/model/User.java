package com.qpf.model;
import java.util.List;
/**
 * 用户表
 **/
public class User {
	private String id;
	private String name;
	private String desc;
	private String age;
	private String gender;
	private String email;
	private String heading;
	private String level;
	private String create_time;
	private String update_time;
	private List<Integer> blogs;
	public User (String id, String name, String desc, String age, String gender, String email, String heading, String level, String create_time, String update_time, List<Integer> blogs){
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.age = age;
		this.gender = gender;
		this.email = email;
		this.heading = heading;
		this.level = level;
		this.create_time = create_time;
		this.update_time = update_time;
		this.blogs = blogs;
		
	}
	public String getId (){
		return this.id
	}
	public String setId (String id){
		this.id = id;
	}
	public String getName (){
		return this.name
	}
	public String setName (String name){
		this.name = name;
	}
	public String getDesc (){
		return this.desc
	}
	public String setDesc (String desc){
		this.desc = desc;
	}
	public String getAge (){
		return this.age
	}
	public String setAge (String age){
		this.age = age;
	}
	public String getGender (){
		return this.gender
	}
	public String setGender (String gender){
		this.gender = gender;
	}
	public String getEmail (){
		return this.email
	}
	public String setEmail (String email){
		this.email = email;
	}
	public String getHeading (){
		return this.heading
	}
	public String setHeading (String heading){
		this.heading = heading;
	}
	public String getLevel (){
		return this.level
	}
	public String setLevel (String level){
		this.level = level;
	}
	public String getCreate_time (){
		return this.create_time
	}
	public String setCreate_time (String create_time){
		this.create_time = create_time;
	}
	public String getUpdate_time (){
		return this.update_time
	}
	public String setUpdate_time (String update_time){
		this.update_time = update_time;
	}
	public List<Integer> getBlogs (){
		return this.blogs
	}
	public List<Integer> setBlogs (List<Integer> blogs){
		this.blogs = blogs;
	}
	
}