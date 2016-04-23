package com.yufeng.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import com.yufeng.service.impl.MessageServiceImpl;

public class TestClientMsg {
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		System.out.println("Attempting connection");
		Socket client = new Socket( InetAddress.getByName( "127.0.0.1" ), 10222 );
		System.out.println("Connected to: " + 
	    client.getInetAddress().getHostName());
		
		dealService(client.getInputStream(),client.getOutputStream());
		
	    client.close();
	}
	public static void dealService(InputStream in,OutputStream out) throws IOException, ClassNotFoundException{
		ObjectOutputStream output = new ObjectOutputStream(out);
		ObjectInputStream input = new ObjectInputStream(in);
		
		MessageServiceImpl service = new MessageServiceImpl();
		service.recieve(in, out);
		System.out.println(Arrays.toString(service.getMsgs()));
		output.close();
	    input.close();
	}
}
