package com.yufeng.service;

public interface LoginService extends SocketService{
	/**
	 * 发送方（客户端）
	 */
	public void sender(String username,String password);
	/**
	 * 接收方
	 */
	public void receiver();
}
