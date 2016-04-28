package com.yufeng.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.yufeng.service.LoginService;
import com.yufeng.service.impl.LoginServiceImpl;

public class TestClientLogin {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("Attempting connection");
		Socket client = new Socket( InetAddress.getByName( "127.0.0.1" ), 10222 );
		System.out.println("Connected to: " + 
	    client.getInetAddress().getHostName());
		
		LoginService service = new LoginServiceImpl();
		service.setSocket(client);
		service.sender("", "");
		System.out.println(service.getState());
		System.out.println(service.message());
	}
}
