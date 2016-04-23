package com.yufeng.service.impl;

import java.io.File;
import java.io.IOException;

import com.yufeng.model.FileItem;
import com.yufeng.service.DownloadService;

public class DownloadServiceImpl extends BaseService implements DownloadService{

	@Override
	public Object sender(Object... args) {
		sender((FileItem)args[0],(File) args[1]);
		return null;
	}

	@Override
	public Object receiver(Object... args) {
		receiver();
		return null;
	}

	@Override
	public void sender(FileItem item, File file) {
		try {
			util.writeObject(item);
			if(item.getType()==FileItem.DIRECTORY){
				int count = util.readInt();
				if(!file.exists()){
					file.mkdirs();
				}
				for (int i = 0; i < count; i++) {
					String temp = (String) util.readObject();
					util.writeToLocal(file.getAbsolutePath()+temp);
				}
			}else{
				String filename = (String) util.readObject();
				util.writeToLocal(file.getAbsolutePath()+"/"+filename);
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
			FileItem item = (FileItem) util.readObject();
			File file = new File(item.getAbsolute());
			if(file.isDirectory()){
				File[] files = file.listFiles();
				util.writeInt(files.length);
				for (int i = 0; i < files.length; i++) {
					util.writeObject(files[i].getName());
					util.readFromLocal(files[i].getAbsolutePath());
				}
			}else{
				util.writeObject(file.getName());//传递参数
				util.readFromLocal(file.getAbsolutePath());
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			try {
				util.writeObject("error");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}


}
