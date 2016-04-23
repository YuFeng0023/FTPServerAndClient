package com.yufeng.test;

import java.io.File;
import java.net.InetAddress;
import java.net.Socket;

import com.yufeng.model.FileItem;
import com.yufeng.service.impl.UploadServiceImpl;
import com.yufeng.service.impl.UploadServiceImpl2;

public class TestClientUploadService {
	public static void main(String[] args) throws Exception {
		System.out.println("Attempting connection");
		Socket client = new Socket( InetAddress.getByName( "127.0.0.1" ), 10222 );
		System.out.println("Connected to: " + 
	    client.getInetAddress().getHostName());
		
		UploadServiceImpl service = new UploadServiceImpl2();
		service.setSocket(client);
		service.sender(new FileItem(FileItem.DIRECTORY,"testA","D:/testA/"),new File("D:/testB/LongPathTool_v2.20.exe"));
		service.close();
	}
}
