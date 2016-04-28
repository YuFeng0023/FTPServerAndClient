package com.yufeng.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.yufeng.service.ItemService;
import com.yufeng.service.UploadService;
import com.yufeng.service.impl.ItemServiceImpl;
import com.yufeng.service.impl.UploadServiceImpl;
import com.yufeng.util.SocketUtil;
import com.yufeng.util.WriteFileUtil;
/**
 * ����socket�����ļ�
 * @author Feng
 *
 */
public class TestServerItemService {
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(10222,100);
		System.out.println("Waiting for connection ");
		Socket connection = server.accept();
		System.out.println("get connection ,"+" received from: " +
	         connection.getInetAddress().getHostName() );
		
		UploadService service = new UploadServiceImpl();
		service.setSocket(connection);
		service.receiver();
		connection.close();
	}
}
