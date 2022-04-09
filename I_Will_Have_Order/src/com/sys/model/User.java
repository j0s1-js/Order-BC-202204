package com.sys.model;
/*
 * 用户类
 */
public class User {	
	private int id;					// 用户ID
	private String username;			// 用户名
	private String password;			// 用户密码
	private double wallet;				// 用户钱包
	
	public User() {}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.wallet = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public double getWallet() {
		return wallet;
	}

	public void setWallet(double wallet) {
		this.wallet = wallet;
	}

}
