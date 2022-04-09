package com.sys.dao;

import java.util.List;

import com.sys.model.Order;

public interface OrderDao {
	public boolean create(Order order);	// 创建订单
	public List<Order> query(int id, String username);		// 查询订单
	public boolean update(int id, String state);	// 修改订单
}
