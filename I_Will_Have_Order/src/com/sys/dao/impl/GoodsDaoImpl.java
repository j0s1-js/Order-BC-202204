package com.sys.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sys.dao.GoodsDao;
import com.sys.model.Goods;
import com.sys.utils.JDBCUtils;

public class GoodsDaoImpl implements GoodsDao {

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

	@Override
	public boolean update(String goodsname, int quantity) {		// 修改商品
		Connection conn = null;
		String sql;
		int num = 0;
		boolean flag = true;
		try {
			conn = JDBCUtils.getConnection();
			sql = "update goods set quantity=quantity-? where goodsname=?";
			num = JDBCUtils.executeUpdate(sql, quantity, goodsname);
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
