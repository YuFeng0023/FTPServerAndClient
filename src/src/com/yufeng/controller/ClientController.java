package com.yufeng.controller;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.yufeng.model.FileItem;
import com.yufeng.service.DownloadService;
import com.yufeng.service.ItemService;
import com.yufeng.service.LoginService;
import com.yufeng.service.SocketService;
import com.yufeng.service.UploadService;
import com.yufeng.service.impl.LoginServiceImpl;
import com.yufeng.service.impl.MessageServiceImpl;
import com.yufeng.util.SocketUtil;

public class ClientController {
	protected Socket client;//客户端的Socket
	/**
	 * 用于传递文本信息
	 */
	private MessageServiceImpl service = new MessageServiceImpl();
	protected int total;//总文件数
	protected int count;//已传递完成数量
	protected Registor registor ;//注册机，从注册机中获取已注册的组件
	protected SocketUtil util;//SocketUtil,封装了对Socket操作的一些方法
	protected int state;
	protected String message;
	/**
	 * 构造函数，必须绑定Socket
	 * @param client
	 */
	public ClientController(Socket client){
		if(client==null){
			throw new RuntimeException("未赋Socket初值！");
		}
		this.client = client;
		registor = new Registor(client);
		try {
			util = new SocketUtil(client);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	/**
//	 * 登录，暂未使用
//	 * @param username
//	 * @param password
//	 * @return
//	 */
//	public boolean login(String username,String password){
//		try {
//			service.send(client.getInputStream(), client.getOutputStream(), new Object[]{1,"Login"});
//			service.send(client.getInputStream(), client.getOutputStream(), new Object[]{2,username,password});
//			service.recieve(client.getInputStream(), client.getOutputStream());
//			String[] msgs = service.getMsgs();
//			return msgs[0].equals("success");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
	
	/**
	 * 上传文件
	 * @param item 单个节点信息
	 * @param file 具体文件
	 */
//	public void uploadFile(FileItem item,File file){
//		try {
//			service.send(client.getInputStream(), client.getOutputStream(), new Object[]{1,"FileUploadSingle"});
//			ReadFileUtil reader = new ReadFileUtil();
//			reader.setOutput(client.getOutputStream());
////			reader.
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	public void uploadFile(FileItem item,File file){
		try {
			if(file.isDirectory()){
				util.writeObject("FileUploadMutiply");
				UploadService service = (UploadService) registor.get("FileUploadMutiply");
				service.sender(item, file);
			}else{
				util.writeObject("FileUploadSingle");
				UploadService service = (UploadService) registor.get("FileUploadSingle");
				service.sender(item, file);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 加载文件节点信息
	 * @param dir 请求目录
	 * @return
	 */
	public List<FileItem> loadItem(String dir){
		try {
			util.writeObject("GetFileItem");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SocketService service = registor.get("GetFileItem");
		return ((ItemService)service).sender(dir);
	}
	/**
	 * 下载文件，加进度条待做
	 * @param item 服务器节点，下一步添加进度条
	 * @param file 本地文件夹
	 */
	public void downloadFile(FileItem item,File file){
//		new Thread(){
//			@Override
//			public void run() {
//				count=1;
//				super.run();
//			}
//		}.start();
		try {
			if(item.getType()==FileItem.DIRECTORY){
					util.writeObject("FileDownloadMutiply");
				((DownloadService)(registor.get("FileDownloadMutiply"))).sender(item, file);
			}else{
				util.writeObject("FileDownloadSingle");
				((DownloadService)(registor.get("FileDownloadSingle"))).sender(item, file);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean login(String username,String password){
		try {
			util.writeObject("Login");
			System.out.println("_____");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoginService service = (LoginService)registor.get("Login");
		service.sender(username, password);
		if(service.getState()==SocketService.ERROR){
			message = service.message();
			return false;
		}else{
			return true;
		}
	}
	public Socket getClient() {
		return client;
	}
	public void setClient(Socket client) {
		this.client = client;
	}
	public int getTotal() {
		return total;
	}
	public int getCount() {
		return count;
	}
	public int getState() {
		return state;
	}
	public String getMessage() {
		return message;
	}
	/**
	 * 关闭连接
	 */
	public void close() {
		try {
			util.writeObject("exit");
			util.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
