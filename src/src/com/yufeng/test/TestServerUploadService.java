package com.yufeng.test;

import java.net.ServerSocket;
import java.net.Socket;

import com.yufeng.service.impl.UploadServiceImpl;
import com.yufeng.service.impl.UploadServiceImpl2;
/**
 * ����socket�����ļ�
 * @author Feng
 *
 */
public class TestServerUploadService {
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(10222,100);
		System.out.println("Waiting for connection ");
		Socket connection = server.accept();
		System.out.println("get connection ,"+" received from: " +
	         connection.getInetAddress().getHostName() );
		
		UploadServiceImpl service = new UploadServiceImpl2();
		service.setSocket(connection);
		service.receiver();
		service.close();
	}
}
