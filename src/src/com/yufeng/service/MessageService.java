package com.yufeng.service;

public interface MessageService extends SocketService {
	/**
	 * 
	 * @param msgs 要传递的对象
	 */
	public void send(String[] msgs);
	/**
	 * 
	 * @return
	 */
	public Object receiver();
}
