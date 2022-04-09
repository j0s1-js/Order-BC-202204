package com.sys.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.sys.dao.impl.GoodsDaoImpl;
import com.sys.dao.impl.OrderDaoImpl;
import com.sys.dao.impl.UserDaoImpl;
import com.sys.md5.MD5;
import com.sys.model.Cart;
import com.sys.model.Goods;
import com.sys.model.Order;
import com.sys.model.User;

public class GoodsView {
	MD5 md5 = new MD5();
	UserDaoImpl userdl = new UserDaoImpl();
	GoodsDaoImpl goodsdl = new GoodsDaoImpl();
	OrderDaoImpl orderdl = new OrderDaoImpl();
	Scanner sc = new Scanner(System.in);
	Cart cart = null;
	User user = null;
	final String str_menu = "[点餐界面]请选择输入:(0)返回    (1)查询所有商品    (2)分类查询     (3)购买商品    (4)查看购物车";
	final String str_cart = "[购物车界面]请选择输入:(0)返回    (1)删除商品    (2)清空购物车    (3)去支付    (4)生成订单";
	
	public GoodsView(User user){
		this.user = user;
	}
	
	public void menu(){	// 主菜单
		int key;
		boolean flag = true;
		do {
			System.out.println(str_menu);
			key = sc.nextInt();
			switch (key) {
			case 0:
				flag = false;
				break;
			case 1:
				query();
				break;
			case 2:
				queryType();
				break;
			case 3:
				buy();
				break;
			case 4:
				cart();
				break;
			}
		} while (flag);
	}
	
	private void query(){		// 查询所有商品
		List<Goods> list = new ArrayList<Goods>();
		if((list = goodsdl.query()).isEmpty()){
			System.out.println("没有商品...");
			return;
		}
		System.out.println("[商品列表]");
		System.out.println("商品ID\t商品名\t价格\t种类\t数量");
		for(Goods goods:list){
			System.out.println(goods.toString());
		}
	}
	
	private void queryType(){		// 分类查询
		List<Goods> list = new ArrayList<Goods>();
		String str;
		System.out.println("[查询商品界面]请输入要查询的商品名称或种类:");
		str = sc.next();
		if((list = goodsdl.queryType(str, str)).isEmpty()){
			System.out.println("没有商品...");
			return;
		}
		System.out.println("[商品列表]");
		System.out.println("商品ID\t商品名\t价格\t种类\t数量");
		for(Goods goods:list){
			System.out.println(goods.toString());
		}
	}
	
	private void buy(){		// 购买商品
		List<Goods> list = null;
		String str;
		int quantity;
		System.out.println("[购买商品界面]请输入要购买的商品名:");
		str = sc.next();
		if((list = goodsdl.queryType(str, null)).isEmpty()){
			System.out.println("没有商品...");
			return;
		}
		Goods goods = list.get(0);
		System.out.println("[购买商品界面]请输入要购买商品的数量:");
		quantity = sc.nextInt();
		if(quantity <= 0){
			System.out.println("购买商品数量必须大于0...");
			return;
		}else if(quantity > goods.getQuantity()){
			System.out.println("库存不足...");
			return;
		}
		goods.setQuantity(quantity);
		if(cart == null){
			Order order = new Order(user.getUsername(), 0, null, "未支付");
			cart = new Cart(order, goods);
		}else {
			list = cart.getGoods();
			for(int i=0; i<list.size(); i++){
				if(list.get(i).getGoodsname().equals(goods.getGoodsname()) == true){
					list.get(i).setQuantity(list.get(i).getQuantity() + quantity);
					System.out.println("商品已存在,已追加" + quantity + "个商品到购物车...");
					return;
				}
			}
			cart.add(goods);
		}
		System.out.println("商品已添加到购物车...");
	}
	
	private void cart(){	// 查看购物车
		System.out.println("[购物车界面]");
		if(cart == null){
			System.out.println("没有购买商品...");
			return;
		}
		List<Goods> list = cart.getGoods();
		System.out.println("商品ID\t商品名\t价格\t种类\t数量");
		for(Goods goods:list){
			System.out.println(goods.toString());
		}
		int key;
		boolean flag = true;
		do{
			System.out.println(str_cart);
			key = sc.nextInt();
			switch (key) {
			case 0:
				flag = false;
				break;
			case 1:
				delete();
				if(cart == null){
					flag = false;
				}
				break;
			case 2:
				clear();
				flag = false;
				break;
			case 3:
				pay();
				if(cart == null){
					flag = false;
				}
				break;
			case 4:
				order();
				if(cart == null){
					flag = false;
				}
				break;
			}
		}while(flag);
	}
	
	private void delete(){	// 删除商品
		String goodsname;
		System.out.println("[删除商品界面]请输入要删除的商品名:");
		goodsname = sc.next();
		List<Goods> list = cart.getGoods();
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getGoodsname().equals(goodsname) == true){
				list.remove(i);
				System.out.println("删除成功...");
				if(list.isEmpty()){
					cart = null;
				}else{
					System.out.println("[购物车界面]");
					System.out.println("商品ID\t商品名\t价格\t种类\t数量");
					for(Goods goods:list){
						System.out.println(goods.toString());
					}
				}
				return;
			}
		}
	}
	
	private void clear() {	// 清空购物车
		cart = null;
		System.out.println("已清空购物车...");
	}
	
	private void pay(){		// 去支付
		String password;
		System.out.println("[支付界面]请输入支付密码:");
		password = sc.next();
		if(md5.getMD5(password).equals(user.getPassword()) == false){
			System.out.println("密码输入错误...");
			return;
		}
		List<Goods> list = cart.getGoods();
		Order order = new Order(user.getUsername(), 0, new Date(), "已支付");
		for(Goods goods:list){
			order.setMoney(order.getMoney() + goods.getPrice() * goods.getQuantity());
		}
		if(user.getWallet() < order.getMoney()){
			System.out.println("余额不足...");
			return;
		}
		if(orderdl.create(order) == true && userdl.recharge(user.getUsername(), -order.getMoney()) == true){
			for(Goods goods:list){
				if(goodsdl.update(goods.getGoodsname(), goods.getQuantity()) == false){
					System.out.println("商品更新错误...");
					return;
				}
			}
			cart = null;
			System.out.println("支付成功...");
		}else {
			System.out.println("支付失败...");
		}
	}
	
	private void order(){	// 生成订单
		List<Goods> list = cart.getGoods();
		Order order = new Order(user.getUsername(), 0, new Date(), "未支付");
		for(Goods goods:list){
			order.setMoney(order.getMoney() + goods.getPrice() * goods.getQuantity());
		}
		if(orderdl.create(order) == true){
			cart = null;
			System.out.println("生成订单成功...");
		}else {
			System.out.println("生成订单失败...");
		}
	}
}
