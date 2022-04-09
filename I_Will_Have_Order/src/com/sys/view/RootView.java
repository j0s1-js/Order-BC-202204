package com.sys.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sys.dao.impl.RootDaoImpl;
import com.sys.model.Goods;

public class RootView {
	RootDaoImpl rootdl = new RootDaoImpl();
	Scanner sc = new Scanner(System.in);
	final String str_home = "[管理员界面]请选择输入:(0)返回    (1)添加商品    (2)删除商品    (3)修改商品    (4)查询商品";
	public void home(){	// 管理员主界面
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
				add();
				break;
			case 2:
				delete();
				break;
			case 3:
				update();
				break;
			case 4:
				query();
				break;
			}
		} while (flag);
	}
	
	private void add(){	// 添加商品
		String goodsname, type;
		double price;
		int quantity;
		System.out.println("[添加商品界面]");
		System.out.println("请输入商品名:");
		goodsname = sc.next();
		System.out.println("请输入商品价格:");
		price = sc.nextDouble();
		if(price <= 0){
			System.out.println("输入错误!商品价格必须大于0!");
			return;
		}
		System.out.println("请输入商品类型:");
		type = sc.next();
		System.out.println("请输入商品数量:");
		quantity = sc.nextInt();
		if(quantity <= 0){
			System.out.println("输入错误!商品数量必须大于0!");
			return;
		}
		if(rootdl.add(new Goods(goodsname, price, type, quantity)) == true){
			System.out.println("添加成功...");
		}else{
			System.out.println("添加失败...");
		}
	}
	
	private void delete(){	// 删除商品
		String goodsname;
		System.out.println("[删除商品界面]请输入要删除的商品名:");
		goodsname = sc.next();
		if(rootdl.delete(goodsname) == true){
			System.out.println("删除成功...");
		}else {
			System.out.println("删除失败...");
		}
	}
	
	private void update(){	// 修改商品
		String type, goodsname;
		int quantity;
		double price;
		System.out.println("[修改商品界面]请输入要修改的商品名:");
		goodsname = sc.next();
		System.out.println("[修改商品界面]请输入商品价格:");
		price = sc.nextDouble();
		if(price <= 0){
			System.out.println("输入错误!商品价格必须大于0!");
			return;
		}
		System.out.println("请输入商品类型:");
		type = sc.next();
		System.out.println("请输入商品数量:");
		quantity = sc.nextInt();
		if(quantity <= 0){
			System.out.println("输入错误!商品数量必须大于0!");
			return;
		}
		Goods goods = new Goods(goodsname, price, type, quantity);
		if(rootdl.update(goods) == true){
			System.out.println("修改成功...");
		}else {
			System.out.println("修改失败...");
		}
	}
	
	private void query(){	// 查询商品
		List<Goods> list = new ArrayList<Goods>();
		String str;
		int key;
		System.out.println("[查询商品界面]请选择输入:(1)查询所有商品    (2)分类查询");
		key = sc.nextInt();
		switch (key) {
		case 1:
			if((list = rootdl.query()).isEmpty()){
				System.out.println("没有商品...");
				return;
			}
			break;
		case 2:
			System.out.println("[查询商品界面]请输入要查询的商品名称或种类:");
			str = sc.next();
			if((list = rootdl.queryType(str, str)).isEmpty()){
				System.out.println("没有商品...");
				return;
			}
			break;
		}
		System.out.println("[商品列表]");
		System.out.println("商品ID\t商品名\t价格\t种类\t数量");
		for(Goods goods:list){
			System.out.println(goods.toString());
		}
	}
}
