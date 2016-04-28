package com.yufeng.service.impl;

import java.io.IOException;
import java.net.Socket;

import com.yufeng.service.SocketService;
import com.yufeng.util.SocketUtil;

public abstract class BaseService implements SocketService {
	protected Socket socket;
	protected int state;//状态
	protected String message;//状态信息
	protected SocketUtil util;
	@Override
	public void setSocket(Socket socket) {
		this.socket = socket;
		try {
			util = new SocketUtil(this.socket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public abstract Object sender(Object... args);

	@Override
	public abstract Object receiver(Object... args);

	@Override
	public int getState() {
		return state;
	}

	@Override
	public String message() {
		return message;
	}
	@Override
	public void close()  {
		try {
			util.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
