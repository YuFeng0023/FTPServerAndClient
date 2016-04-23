package com.yufeng.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.yufeng.util.WriteFileUtil;
/**
 * ����socket�����ļ�
 * @author Feng
 *
 */
public class TestServerFile {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(10222,100);
		System.out.println("Waiting for connection ");
		Socket connection = server.accept();
		System.out.println("get connection ,"+" received from: " +
	         connection.getInetAddress().getHostName() );
		
		
		ObjectOutputStream output = new ObjectOutputStream( connection.getOutputStream() );
	    output.flush();
	    
	    ObjectInputStream input = new ObjectInputStream( connection.getInputStream() );
	    System.out.println("start");
        try {
			String temp ;
			while((temp=(String) input.readObject()).equals("filestart")){
				WriteFileUtil writer = new WriteFileUtil();
				writer.setInput(input);
				writer.setFilename("D:/testUtil.txt");
				writer.writeFile();
				break;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        connection.close();
	}
}
