package com.sys.model;
/*
 * 商品类
 */
public class Goods {
	private int id;			// 商品ID
	private String goodsname;	// 商品名
	private	double price;		// 商品价格
	private String type;		// 商品类型
	private int quantity;		// 商品数量
	
	public Goods() {}
	
	public Goods(String goodsname, double price, String type, int quantity) {
		this.goodsname = goodsname;
		this.price = price;
		this.type = type;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return id + "\t" + goodsname + "\t" + price + "\t" + type + "\t" + quantity;
	}
	
}
