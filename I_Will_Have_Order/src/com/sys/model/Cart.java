package com.sys.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private List<Goods> goods = new ArrayList<>();
	private Order order = new Order();
	
	public Cart() {}
	
	public Cart(Order order, Goods goods) {
		this.order = order;
		this.goods.add(goods);
	}
	
	public void add(Goods goods){
		this.goods.add(goods);
	}

	public List<Goods> getGoods() {
		return goods;
	}

	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}
