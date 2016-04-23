package com.yufeng.test;

import java.io.File;
import java.net.InetAddress;
import java.net.Socket;

import com.yufeng.model.FileItem;
import com.yufeng.service.UploadService;
import com.yufeng.service.impl.UploadServiceImpl;

public class TestClientFileItemService {
	public static void main(String[] args) throws Exception {
		System.out.println("Attempting connection");
		Socket client = new Socket( InetAddress.getByName( "127.0.0.1" ), 10222 );
		System.out.println("Connected to: " + 
	    client.getInetAddress().getHostName());
		
		UploadService service = new UploadServiceImpl();
		service.setSocket(client);
//		service.sender(new FileItem(FileItem.DIRECTORY,"testA","D:/testA"), new File("D:/testB/testUtil.txt"));
		service.sender(new FileItem(FileItem.DIRECTORY,"testA","D:/testA"), new File("D:/testB/"));
		client.close();
	}
}
