package com.sys.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sys.dao.RootDao;
import com.sys.model.Goods;
import com.sys.model.Root;
import com.sys.utils.JDBCUtils;
/*
 * 管理员类接口的实现类：操作数据库
 */
public class RootDaoImpl implements RootDao {
	Connection conn = null;
	String sql;
	int num = 0;
	boolean flag = true;
	ResultSet rs = null;
	
	@Override
	public boolean login(Root root) {	// 管理员登录方法
		Connection conn = null;
		String sql;
		ResultSet rs = null;
		boolean flag = true;
		try {
			conn = JDBCUtils.getConnection();
			sql = "SELECT rootname,password FROM root where rootname=? and password=?";
			rs = JDBCUtils.executeQuery(sql, root.getRootname(), root.getPassword());
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
	public boolean add(Goods goods) {	// 添加商品
		Connection conn = null;
		String sql;
		int num = 0;
		boolean flag = true;
		try {
			conn = JDBCUtils.getConnection();
			sql = "insert into goods(goodsname,price,type,quantity)values(?,?,?,?)";
			num = JDBCUtils.executeUpdate(sql, goods.getGoodsname(), goods.getPrice(), goods.getType(), goods.getQuantity());
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
	public boolean delete(String goodsname) {	// 删除商品
		Connection conn = null;
		String sql;
		int num = 0;
		boolean flag = true;
		try {
			conn = JDBCUtils.getConnection();
			sql = "delete from goods where goodsname=?";
			num = JDBCUtils.executeUpdate(sql, goodsname);
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
	public boolean update(Goods goods) {	// 修改商品
		Connection conn = null;
		String sql;
		int num = 0;
		boolean flag = true;
		try {
			conn = JDBCUtils.getConnection();
			sql = "update goods set price=?,type=?,quantity=? where goodsname=?";
			num = JDBCUtils.executeUpdate(sql, goods.getPrice(), goods.getType(), goods.getQuantity(), goods.getGoodsname());
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
	public List<Goods> query() {	// 查询所有商品
		List<Goods> list = new ArrayList<Goods>();
		Connection conn = null;
		String sql;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			sql = "SELECT * FROM goods";
			rs = JDBCUtils.executeQuery(sql);
			while (rs.next()) {
				Goods goods = new Goods();
				goods.setId(Integer.valueOf(rs.getString("id")));
				goods.setGoodsname(rs.getString("goodsname"));
				goods.setPrice(Double.valueOf(rs.getString("price")));
				goods.setType(rs.getString("type"));
				goods.setQuantity(Integer.valueOf(rs.getString("quantity")));
				list.add(goods);
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
	public List<Goods> queryType(String goodsname, String type) {		// 以名称或种类查询商品
		List<Goods> list = new ArrayList<Goods>();
		Connection conn = null;
		String sql;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			sql = "SELECT * FROM goods where goodsname=? or type=?";
			rs = JDBCUtils.executeQuery(sql, goodsname, type);
			while (rs.next()) {
				Goods goods = new Goods();
				goods.setId(Integer.valueOf(rs.getString("id")));
				goods.setGoodsname(rs.getString("goodsname"));
				goods.setPrice(Double.valueOf(rs.getString("price")));
				goods.setType(rs.getString("type"));
				goods.setQuantity(Integer.valueOf(rs.getString("quantity")));
				list.add(goods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sql = null;
			JDBCUtils.closeAll(conn, null, rs);
		}
		return list;
	}

}
