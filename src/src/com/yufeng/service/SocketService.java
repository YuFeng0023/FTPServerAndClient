package com.yufeng.service;

import java.net.Socket;

public interface SocketService {
	final static Integer SUCCESS = 0;
	final static Integer ERROR = 1;
	/**
	 * 设置Socket
	 * @param socket
	 */
	void setSocket(Socket socket);
	/**
	 * 发送消息
	 * @param args
	 * @return TODO
	 */
	Object sender(Object...args) ;
	/**
	 * 接收消息
	 * @param args
	 * @return TODO
	 */
	Object receiver(Object...args) ;
	/**
	 * 获得状态码
	 * @return
	 */
	int getState();
	/**
	 * 获得信息
	 * @return
	 */
	String message(); 
	
	void close();
}
