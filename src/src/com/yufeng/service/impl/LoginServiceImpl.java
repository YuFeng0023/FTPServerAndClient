package com.yufeng.service.impl;

import java.io.IOException;

import com.yufeng.config.ConfigReader;
import com.yufeng.service.LoginService;
import com.yufeng.service.SocketService;
import com.yufeng.util.PropertyFileReader;

public class LoginServiceImpl extends BaseService implements LoginService{

	@Override
	public void sender(String username, String password) {
		try {
			util.writeObject(username);
			util.writeObject(password);
			String state = (String) util.readObject();
			this.state = state.equals("success")?SocketService.SUCCESS:SocketService.ERROR;
			message = (String) util.readObject();
		} catch (ClassNotFoundException|IOException e) {
			e.printStackTrace();
			this.state = SocketService.ERROR;
			message = "参数错误";
		} 
	}
	@Override
	public void receiver() {
		try {
			String username = (String) util.readObject();
			String password = (String) util.readObject();
			String suser = ConfigReader.get("username");
			String psd = ConfigReader.get("password");
			if(username.equals(suser)&&password.equals(psd)){
				util.writeObject("success");
				util.writeObject("登录成功");
			}else{
				util.writeObject("fail");
				util.writeObject("用户名或密码错误！");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public Object sender(Object... args) {
		sender((String)args[0],(String)args[1]);
		return null;
	}

	@Override
	public Object receiver(Object... args) {
		receiver();
		return null;
	}

}
