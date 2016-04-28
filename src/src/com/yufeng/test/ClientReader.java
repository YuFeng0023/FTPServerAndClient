package com.yufeng.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.yufeng.util.ReadFileUtil;

public class ClientReader {
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		System.out.println("Attempting connection");
		Socket client = new Socket( InetAddress.getByName( "127.0.0.1" ), 10222 );
		System.out.println("Connected to: " + 
	    client.getInetAddress().getHostName());
		
		// set up output stream for objects
	    ObjectOutputStream output = new ObjectOutputStream( client.getOutputStream() );      
	    output.flush(); // flush output buffer to send header information
	
	    // set up input stream for objects
	    ObjectInputStream input = new ObjectInputStream( client.getInputStream() );
	    System.out.println("Got I/O streams");
	   
	    System.out.println(input.readObject());
	    output.close();
	    input.close();
	    client.close();
	}
}
