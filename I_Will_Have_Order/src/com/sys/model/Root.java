package com.sys.model;
/*
 * 管理员类
 */
public class Root {
	private int id;						// 管理员ID
	private String rootname;			// 管理员名
	private String password;			// 管理员密码
	
	public Root() {	}
	
	public Root(String rootname, String password) {	
		this.rootname = rootname;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRootname() {
		return rootname;
	}
	public void setRootname(String rootname) {
		this.rootname = rootname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
