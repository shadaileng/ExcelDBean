package com.qpf.model;
/**
 * 日志表
 **/
public class Blog {
	private String id;
	private String title;
	private String content;
	private String user_id;
	private String create_time;
	private String update_time;
	public Blog (String id, String title, String content, String user_id, String create_time, String update_time){
		this.id = id;
		this.title = title;
		this.content = content;
		this.user_id = user_id;
		this.create_time = create_time;
		this.update_time = update_time;
		
	}
	public String getId (){
		return this.id
	}
	public String setId (String id){
		this.id = id;
	}
	public String getTitle (){
		return this.title
	}
	public String setTitle (String title){
		this.title = title;
	}
	public String getContent (){
		return this.content
	}
	public String setContent (String content){
		this.content = content;
	}
	public String getUser_id (){
		return this.user_id
	}
	public String setUser_id (String user_id){
		this.user_id = user_id;
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
	
}