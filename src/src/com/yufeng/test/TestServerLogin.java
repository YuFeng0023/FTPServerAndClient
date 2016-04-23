package com.yufeng.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.yufeng.service.LoginService;
import com.yufeng.service.impl.LoginServiceImpl;

public class TestServerLogin {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(10222,100);
		System.out.println("Waiting for connection ");
		Socket connection = server.accept();
		System.out.println("get connection ,"+" received from: " +
	         connection.getInetAddress().getHostName() );
		LoginService service = new LoginServiceImpl();
		service.setSocket(connection);
		service.receiver();
	}
}
