package com.yufeng.controller;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yufeng.config.ProtocolPropertiesReader;
import com.yufeng.config.ProtocolReader;
import com.yufeng.service.SocketService;

public class Registor {
	public  Map<String,SocketService> regiestor = new HashMap<>();
	public Registor(Socket socket){
		createItem();
		registSoket(socket);
	}
	public void createItem(){
//		regiestor.put("FileUploadSingle", new UploadServiceImpl2() );
//		regiestor.put("FileUploadMutiply", new UploadServiceImpl2() );
//		regiestor.put("FileDownloadSingle", new DownloadServiceImpl2() );
//		regiestor.put("FileDownloadMutiply", new DownloadServiceImpl2() );
//		regiestor.put("GetFileItem", new ItemServiceImpl() );
		List<String> protocols = ProtocolReader.read();
		for(String p : protocols){
			try {
				regiestor.put(p,(SocketService) Class.forName(ProtocolPropertiesReader.get(p)).newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	public void registSoket(Socket socket){
		for (SocketService service : regiestor.values()) {
			service.setSocket(socket);
		}
	}
	public  SocketService get(String protocol){
		return regiestor.get(protocol);
	}
}
