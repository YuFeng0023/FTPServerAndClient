package com.yufeng.service.impl;

import java.io.File;
import java.io.IOException;

import com.yufeng.model.FileItem;
import com.yufeng.service.UploadService;
import com.yufeng.util.FileUtil;

public class UploadServiceImpl extends BaseService implements UploadService {
	protected int count;
	protected int total;
	@Override
	public void sender(FileItem item, File file) {
		try {
			if(item.getType()==0){
				if(file.isDirectory()){
					util.writeObject("FileUploadMutiply");
					uploadMultiplyFile(item, file);
				}else{
					util.writeObject("FileUploadSingle");
					uploadSingleFile(item, file);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 上传单个文件
	 * @param item
	 * @param file
	 */
	public void uploadSingleFile(FileItem item, File file){
		try {
			util.writeObject(item.getAbsolute());
			util.writeObject(file.getName());//传递参数
			String msg = (String) util.readObject();
			if(msg.equalsIgnoreCase("ok")){
				util.readFromLocal(file.getAbsolutePath());
				state = SUCCESS;
			}else{
				state = ERROR;
				message = msg;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void uploadMultiplyFile(FileItem item, File file){
		try {
			util.writeObject(item.getAbsolute());
			util.writeObject(file.getName());//传递参数
			String msg = (String) util.readObject();
			if(msg.equalsIgnoreCase("ok")){
				File[] files = file.listFiles();
				total = files.length;
				util.writeInt(files.length);
				for (int i = 0; i < files.length; i++) {
					util.writeObject(files[i].getName());
					util.readFromLocal(files[i].getAbsolutePath());
					count++;
				}
				state = SUCCESS;
			}else{
				state = ERROR;
				message = msg;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receiver() {
		try {
			String protocol = (String) util.readObject();
			if(protocol.equals("FileUploadSingle")){
				downloadSingleFile();
			}else{
				if(protocol.equals("FileUploadMutiply"))
					downloadMultiplyFile();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 下载单个文件夹
	 */
	protected void downloadSingleFile(){
		try {
			String path = (String) util.readObject();
			String filename = (String) util.readObject();
			
			if(FileUtil.fileExist(path, filename)){
				util.writeObject("已有同名文件！");
			}else{
				util.writeObject("ok");
				util.writeToLocal(path+"/"+filename);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected void downloadMultiplyFile(){
		try {
			String path = (String) util.readObject();
			String filename = (String) util.readObject();
//			if(FileUtil.fileExist(path, filename)){
//				util.writeObject("已有同名文件夹！");
//			}else{
				util.writeObject("ok");
				int count = util.readInt();
				File dir = new File(path+"/"+filename);
				if(!dir.exists()){
					dir.mkdirs();
				}
				for (int i = 0; i < count; i++) {
					String temp = (String) util.readObject();
					util.writeToLocal(path+"/"+filename+"/"+temp);
				}
//			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public Object sender(Object... args) {
		if(args.length!=2){
			return null;
		}
		sender((FileItem)args[0], (File)args[1]);
		return null;
	}

	@Override
	public Object receiver(Object... args) {
		receiver();
		return null;
	}
	public int getCount() {
		return count;
	}
	public int getTotal() {
		return total;
	}
}
