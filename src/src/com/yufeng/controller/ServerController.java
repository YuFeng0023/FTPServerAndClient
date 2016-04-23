package com.yufeng.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.yufeng.util.SocketUtil;

public class ServerController {
	private static final String address = "127.0.0.1";
	private static final Integer port = 10222;
	private static final Integer max = 100;
	public static void main(String[] args) throws UnknownHostException, IOException {
		ServerSocket server = new ServerSocket(port,max);
		System.out.println("Waiting for connection ");
		while(true){
			new Server(server.accept()).start();
		}
	}
	/**
	 * 服务器类继承线程，实例化时必须绑定Socket
	 * @author Feng
	 *
	 */
	public static class Server extends Thread{
		private Socket connection;
		public Server(Socket server){
			connection = server;
		}
		@Override
		public void run() {
			System.out.println("get connection ,"+" received from: " +
			         connection.getInetAddress().getHostName() );
				try {
					Registor reg = new Registor(connection);
					SocketUtil util = new SocketUtil(connection);
					String protocol ;
					while((protocol = (String) util.readObject())!=null&&!protocol.equals("exit")){
						System.out.println(protocol);
						reg.regiestor.get(protocol).receiver();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
}
