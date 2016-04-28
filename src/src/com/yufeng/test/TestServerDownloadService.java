package com.yufeng.test;

import java.net.ServerSocket;
import java.net.Socket;

import com.yufeng.service.impl.DownloadServiceImpl;
import com.yufeng.service.impl.DownloadServiceImpl2;
/**
 * ����socket�����ļ�
 * @author Feng
 *
 */
public class TestServerDownloadService {
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(10223,100);
		System.out.println("Waiting for connection ");
		Socket connection = server.accept();
		System.out.println("get connection ,"+" received from: " +
	         connection.getInetAddress().getHostName() );
		
//		DownloadServiceImpl service = new DownloadServiceImpl();
		DownloadServiceImpl service = new DownloadServiceImpl2();
		service.setSocket(connection);
		service.receiver();
		service.close();
	}
}
