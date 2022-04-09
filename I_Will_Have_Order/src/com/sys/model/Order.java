package com.sys.model;
import java.text.SimpleDateFormat;
/*
 * 订单类
 */
import java.util.Date;

public class Order {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private int id;			// 订单号
	private String username;	// 用户名
	private double money;		// 支付金额
	private Date date;			// 支付日期
	private String state;		// 订单状态
	
	public Order(){}

	public Order(String username, double money, Date date, String state) {
		this.username = username;
		this.money = money;
		this.date = date;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return id + "\t" + username + "\t" + money + "\t" + sdf.format(date) + "\t" + state;
	}
}
