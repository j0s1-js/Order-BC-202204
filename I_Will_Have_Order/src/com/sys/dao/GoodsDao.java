package com.sys.dao;

import java.util.List;

import com.sys.model.Goods;

public interface GoodsDao {
	public List<Goods> query();					// 查询所有商品
	public List<Goods> queryType(String goodsname, String type);	// 以名称或种类查询商品
	public boolean update(String goodsname, int quantity);			// 修改商品
}
