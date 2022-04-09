package com.sys.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sys.dao.OrderDao;
import com.sys.model.Order;
import com.sys.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean create(Order order) {	// 创建订单
		Connection conn = null;
		String sql;
		int num = 0;
		boolean flag = true;
		try {
			conn = JDBCUtils.getConnection();
			sql = "insert into orders(username,money,date,state) values(?,?,?,?)";
			num = JDBCUtils.executeUpdate(sql, order.getUsername(), order.getMoney(), order.getDate(), order.getState());
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
	public List<Order> query(int id, String username) {	// 查询订单
		List<Order> list = new ArrayList<>();
		Connection conn = null;
		String sql;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			sql = "SELECT * FROM orders where id=? or username=?";
			rs = JDBCUtils.executeQuery(sql, id, username);
			while (rs.next()) {
				Order order = new Order();
				order.setId(Integer.valueOf(rs.getString("id")));
				order.setUsername(rs.getString("username"));
				order.setMoney(Double.valueOf(rs.getString("money")));
				order.setDate(sdf.parse(rs.getString("date")));
				order.setState(rs.getString("state"));
				list.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sql = null;
			JDBCUtils.closeAll(conn, null, rs);
		}
		return list;
	}

	@Override
	public boolean update(int id, String state) {
		Connection conn = null;
		String sql;
		int num = 0;
		boolean flag = true;
		try {
			conn = JDBCUtils.getConnection();
			sql = "update orders set state=? where id=?";
			num = JDBCUtils.executeUpdate(sql, state, id);
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
