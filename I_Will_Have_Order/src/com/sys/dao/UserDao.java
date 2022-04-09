package com.sys.dao;

import com.sys.model.User;

public interface UserDao {
	public boolean register(User user);	 	// 注册
	public boolean login(User user);		// 登录
	public User message(String username);	// 个人信息
	public boolean recharge(String username, double money);		// 充值
}
