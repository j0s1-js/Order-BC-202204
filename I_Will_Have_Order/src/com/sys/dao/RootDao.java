package com.sys.dao;

import java.util.List;

import com.sys.model.Goods;
import com.sys.model.Root;

public interface RootDao {
	public boolean login(Root root);		// 登录
	public boolean add(Goods goods);		// 添加商品
	public boolean delete(String goodsname);		// 删除商品
	public boolean update(Goods goods);			// 修改商品
	public List<Goods> query();					// 查询所有商品
	public List<Goods> queryType(String goodsname, String type);	// 以名称或种类查询商品
}
