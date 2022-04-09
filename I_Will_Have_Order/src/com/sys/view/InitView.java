package com.sys.view;

import java.util.Scanner;

import com.sys.dao.impl.RootDaoImpl;
import com.sys.dao.impl.UserDaoImpl;
import com.sys.model.Root;
import com.sys.model.User;
/*
 * 初始界面类
 */
public class InitView {
	UserDaoImpl userdl = new UserDaoImpl();
	RootDaoImpl rootdl = new RootDaoImpl();
	Scanner sc = new Scanner(System.in);
	final String str_init = "[登录界面]请选择输入:(0)退出    (1)登录    (2)注册    (3)管理员登录";
	public void init(){	// 初始界面
		int key;
		boolean flag = true;
		do {
			System.out.println(str_init);
			key = sc.nextInt();
			switch (key) {
			case 0:
				flag = false;
				break;
			case 1:
				login();
				break;
			case 2:
				register();
				break;
			case 3:
				rootlogin();
				break;
			}
		} while (flag);
		System.out.println("谢谢使用！");
		sc.close();
	}
	
	private void login(){	// 登录界面
		String username, password;
		System.out.println("请输入您的用户名:");
		username = sc.next();
		if(username.matches("\\w{6,20}") == false){
			System.out.println("请输入6~20位由字母或数字组成的用户名...");
			return;
		}
		System.out.println("请输入您的密码:");
		password = sc.next();
		if(password.matches("\\w{6,16}") == false){
			System.out.println("请输入6~16位由字母或数字组成的密码...");
			return;
		}
		User user = new User(username, password);
		if(userdl.login(user) == true){
			System.out.println("登录成功...");
			new UserView(user).home();
		}else{
			System.out.println("登录失败...");
		}
	}
	
	private void register(){	// 注册界面
		String username, password, password2;
		System.out.println("请输入您的用户名:");
		username = sc.next();
		if(username.matches("\\w{6,20}") == false){
			System.out.println("请输入6~20位由字母或数字组成的用户名...");
			return;
		}
		System.out.println("请输入您的密码:");
		password = sc.next();
		if(password.matches("\\w{6,16}") == false){
			System.out.println("请输入6~16位由字母或数字组成的密码...");
			return;
		}
		System.out.println("请重复您的密码:");
		password2 = sc.next();
		if(password.equals(password2) == false){
			System.out.println("两次密码不一致...");
			return;
		}
		if(userdl.register(new User(username, password)) == true){
			System.out.println("注册成功...");
		}else{
			System.out.println("注册失败...");
		}
	}
	
	private void rootlogin(){	// 管理员登录
		String username, password;
		System.out.println("请输入您的管理员账号:");
		username = sc.next();
		System.out.println("请输入您的管理员密码:");
		password = sc.next();
		if(rootdl.login(new Root(username, password)) == true){
			System.out.println("登录成功...");
			new RootView().home();
		}else{
			System.out.println("登录失败...");
		}
	}
	
}
