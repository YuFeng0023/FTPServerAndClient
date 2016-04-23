package com.yufeng.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.yufeng.service.impl.MessageServiceImpl;
public class TestServerMsg {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(10222,100);
		System.out.println("Waiting for connection ");
		Socket connection = server.accept();
		System.out.println("get connection ,"+" received from: " +
	         connection.getInetAddress().getHostName() );
		
		dealService(connection.getInputStream(),connection.getOutputStream());
		
        connection.close();
	}
	public static void dealService(InputStream in,OutputStream out) throws IOException{
		ObjectOutputStream output = new ObjectOutputStream(out);
		ObjectInputStream input = new ObjectInputStream(in);
		
		MessageServiceImpl service = new MessageServiceImpl();
//		service.send(in, out, new Object[]{4,"asdf","No.2","No.3"});
//		service.send(in, out, new Object[]{1,"asdf"});
		output.close();
	    input.close();
	}
}
