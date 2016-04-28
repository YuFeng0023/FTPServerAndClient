package com.yufeng.service.impl;

import java.io.File;
import java.io.IOException;

import com.yufeng.model.FileItem;
import com.yufeng.service.UploadService;

/**
 * 版本一传递文件夹时，加入文件夹中还有其他文件夹时就会报错 版本二消除这一BUG
 * @author Feng
 */
public class UploadServiceImpl2 extends UploadServiceImpl implements UploadService {
	@Override
	public void sender(FileItem item, File file) {
		try {
			if (item.getType() == FileItem.DIRECTORY) {
				util.writeObject(item);
				if (file.isDirectory()) {
					writeDirectory(file);
				} else {
					writeFile(file);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 读取文件夹，写入到本地
	 * @param file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void readDirectory(File file) throws IOException, ClassNotFoundException {
		String path = (String) util.readObject();
		File newDir = new File(file.getAbsolutePath() + "/" + path);
		if (!newDir.exists()) {
			newDir.mkdirs();
		}
		int count = util.readInt();
		for (int i = 0; i < count; i++) {
			int type = util.readInt();
			if (type == FileItem.DIRECTORY) {
				readDirectory(newDir);
			} else {
				readFile(newDir);
			}
		}
	}
	/**
	 * 读取文件，写到本地
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void readFile(File file) throws ClassNotFoundException, IOException {
		String filename = (String) util.readObject();
		util.writeToLocal(file.getAbsolutePath() + "/" + filename);
	}
	/**
	 * 写文件夹
	 * @param file
	 * @throws IOException
	 */
	public void writeDirectory(File file) throws IOException {
		util.writeInt(FileItem.DIRECTORY);
		util.writeObject(file.getName());
		File[] files = file.listFiles();
		util.writeInt(files.length);
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				writeDirectory(files[i]);
			} else {
				writeFile(files[i]);
			}
		}
	}

	/**
	 * 写单个文件
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void writeFile(File file) throws IOException {
		util.writeInt(FileItem.FILE);
		util.writeObject(file.getName());// 传递参数
		util.readFromLocal(file.getAbsolutePath());
	}
	@Override
	public void receiver() {
		try {
			FileItem item = (FileItem)util.readObject();
			File file = new File(item.getAbsolute());
			int type = util.readInt();
			if(type==FileItem.DIRECTORY){
				readDirectory(file);
			}else{
				readFile(file);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
