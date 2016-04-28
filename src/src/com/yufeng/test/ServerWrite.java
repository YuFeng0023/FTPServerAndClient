package com.yufeng.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

import com.yufeng.service.FileItemService;

/**
 * 只向客户端写信息
 * @author Feng
 *
 */
public class ServerWrite {
	static final int PORT =10222;
	static final int MAXCONNECTION =100;
	protected ObjectOutputStream output;
	protected ObjectInputStream input; 
	protected ServerSocket server;
	protected Socket connection;
	public void init(){
		try {
			server = new ServerSocket(10222,100);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void accept(){
		try {
			connection = server.accept();
			output = new ObjectOutputStream( connection.getOutputStream() );
			input = new ObjectInputStream( connection.getInputStream() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close(){
		try {
			input.close();
			output.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void write(Object obj){
		init();
		accept();
		try {
			output.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		close();
	}
	@Test
	public void writeFileItem(){
		write(FileItemService.getFileItem(null));
	}
}
