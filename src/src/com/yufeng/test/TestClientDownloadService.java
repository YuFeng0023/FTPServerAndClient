package com.yufeng.test;

import java.io.File;
import java.net.InetAddress;
import java.net.Socket;

import com.yufeng.model.FileItem;
import com.yufeng.service.impl.DownloadServiceImpl;
import com.yufeng.service.impl.DownloadServiceImpl2;

public class TestClientDownloadService {
	public static void main(String[] args) throws Exception {
		System.out.println("Attempting connection");
		Socket client = new Socket( InetAddress.getByName( "127.0.0.1" ), 10223 );
		System.out.println("Connected to: " + 
	    client.getInetAddress().getHostName());
		
//		DownloadServiceImpl service = new DownloadServiceImpl();
		DownloadServiceImpl service = new DownloadServiceImpl2();
		service.setSocket(client);
		service.sender(new FileItem(FileItem.DIRECTORY,"testA","D:/testA/"),new File("D:/testB/"));
		service.close();
	}
}
