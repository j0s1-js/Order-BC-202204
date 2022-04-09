package com.sys.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;

import com.sys.dao.UserDao;
import com.sys.md5.MD5;
import com.sys.model.User;
import com.sys.utils.JDBCUtils;

/*
 * 用户类接口的实现类：操作数据库
 */
public class UserDaoImpl implements UserDao {
	private MD5 md5 = new MD5();
	
	@Override
	public boolean register(User user) {	// 注册方法
		Connection conn = null;
		String sql;
		int num = 0;
		boolean flag = true;
		try {
			conn = JDBCUtils.getConnection();
			sql = "insert into user(username,password,wallet) values(?,?,?)";
			num = JDBCUtils.executeUpdate(sql, user.getUsername(), md5.getMD5(user.getPassword()), user.getWallet());
			if (num != 1) {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sql = null;
			JDBCUtils.closeAll(conn, null, null);
		}
		return flag;
	}

	@Override
	public boolean login(User user) {		// 登录方法
		Connection conn = null;
		String sql;
		ResultSet rs = null;
		boolean flag = true;
		try {
			conn = JDBCUtils.getConnection();
			sql = "SELECT * FROM user where username=? and password=?";
			rs = JDBCUtils.executeQuery(sql, user.getUsername(), md5.getMD5(user.getPassword()));
			if (rs.next() == false) {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sql = null;
			JDBCUtils.closeAll(conn, null, rs);
		}
		return flag;
	}

	@Override
	public User message(String username) {		// 个人信息
		User user = new User();
		Connection conn = null;
		String sql;
		ResultSet rs = null;
		boolean flag = false;
		try {
			conn = JDBCUtils.getConnection();
			sql = "SELECT * FROM user where username=?";
			rs = JDBCUtils.executeQuery(sql, username);
			if (rs.next() == true) {
				user.setId(Integer.valueOf(rs.getString("id")));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setWallet(Double.valueOf(rs.getString("wallet")));
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sql = null;
			JDBCUtils.closeAll(conn, null, rs);
		}
		if(flag == true){
			return user;
		}else{
			return null;
		}
	}
	
	@Override
	public boolean recharge(String username, double money) {	// 充值方法
		Connection conn = null;
		String sql;
		int num = 0;
		boolean flag = true;
		try {
			conn = JDBCUtils.getConnection();
			sql = "update user set wallet=wallet+? where username=?";
			num = JDBCUtils.executeUpdate(sql, money, username);
			if (num != 1) {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sql = null;
			JDBCUtils.closeAll(conn, null, null);
		}
		return flag;
	}

}
