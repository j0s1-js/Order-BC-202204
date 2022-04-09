package com.sys.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sys.dao.impl.OrderDaoImpl;
import com.sys.dao.impl.UserDaoImpl;
import com.sys.md5.MD5;
import com.sys.model.Order;
import com.sys.model.User;

public class UserView {
	MD5 md5 = new MD5();
	UserDaoImpl userdl = new UserDaoImpl();
	OrderDaoImpl orderdl = new OrderDaoImpl();
	User user = null;
	Scanner sc = new Scanner(System.in);
	final String str_home = "[用户界面]请选择输入:(0)返回    (1)点餐    (2)查看订单    (3)个人信息    (4)充值";
	
	public UserView(User user) {
		this.user = user;
		init();
	}
	public void home(){	// 用户主界面
		int key;
		boolean flag = true;
		do {
			System.out.println(str_home);
			key = sc.nextInt();
			switch (key) {
			case 0:
				flag = false;
				break;
			case 1:
				new GoodsView(user).menu();
				break;
			case 2:
				order();
				break;
			case 3:
				message();
				break;
			case 4:
				recharge();
				break;
			}
		} while (flag);
	}
	
	private void order(){		// 查看订单
		List<Order> list = new ArrayList<>();
		System.out.println("[订单界面]");
		if((list = orderdl.query(0, user.getUsername())).isEmpty()){
			System.out.println("没有订单...");
			return;
		}
		System.out.println("订单号\t用户名\t支付金额\t日期\t\t\t订单情况");
		for(Order order:list){
			System.out.println(order.toString());
		}
		int key;
		System.out.println("[订单界面]请选择输入:(1)支付订单    (2)取消订单");
		key = sc.nextInt();
		switch (key) {
		case 1:
			pay();
			break;
		case 2:
			cancel();
			break;
		}
	}
	
	private void pay(){		// 支付订单
		List<Order> list = new ArrayList<>();
		int id;
		String password;
		System.out.println("[支付界面]请输入要支付的订单号:");
		id = sc.nextInt();
		if((list = orderdl.query(id, null)).isEmpty() || list.get(0).getUsername().equals(user.getUsername()) == false){
			System.out.println("没有订单...");
			return;
		}
		Order order = list.get(0);
		if(order.getState().equals("已支付") == true){
			System.out.println("订单已支付，无法再次支付...");
			return;
		}else if(order.getState().equals("已取消") == true){
			System.out.println("订单已取消，无法再次支付...");
			return;
		}
		System.out.println("[支付界面]请输入支付密码:");
		password = sc.next();
		if(md5.getMD5(password).equals(user.getPassword()) == false){
			System.out.println("密码输入错误...");
			return;
		}
		if(user.getWallet() < order.getMoney()){
			System.out.println("余额不足...");
			return;
		}
		if(orderdl.update(id, "已支付") == true && userdl.recharge(user.getUsername(), -order.getMoney()) == true){
			System.out.println("支付成功...");
		}else {
			System.out.println("支付失败...");
		}
	}
	
	private void cancel(){		// 取消订单
		List<Order> list = new ArrayList<>();
		int id;
		System.out.println("[取消订单]请输入要取消的订单号:");
		id = sc.nextInt();
		if((list = orderdl.query(id, null)).isEmpty() || list.get(0).getUsername().equals(user.getUsername()) == false){
			System.out.println("没有订单...");
			return;
		}
		Order order = list.get(0);
		if(order.getState().equals("已支付") == true){
			System.out.println("订单已支付，无法取消...");
			return;
		}else if(order.getState().equals("已取消") == true){
			System.out.println("订单已取消，无法再次取消...");
			return;
		}
		if(orderdl.update(id, "已取消") == true){
			System.out.println("取消成功...");
		}else {
			System.out.println("取消失败...");
		}
	}
	
	private void message(){		// 个人信息
		init();
		System.out.println("[个人信息]");
		System.out.println("用户名:" + user.getUsername());
		System.out.println("账户余额:" + user.getWallet());
	}
	
	private void recharge(){	// 充值
		double money;
		System.out.println("[充值界面]请输入充值金额:");
		money = sc.nextDouble();
		if(money <= 0){
			System.out.println("输入金额必须大于0...");
			return;
		}
		if(userdl.recharge(user.getUsername(), money) == true){
			System.out.println("充值成功...");
		}else {
			System.out.println("充值失败...");
		}
	}
	
	private void init(){
		User user = null;
		if((user = userdl.message(this.user.getUsername())) != null){
			this.user.setId(user.getId());
			this.user.setPassword(user.getPassword());
			this.user.setWallet(user.getWallet());
		}
	}
}
