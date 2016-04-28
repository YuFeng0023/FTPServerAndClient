package com.yufeng.util;

import java.io.File;

public class FileUtil {
	/**
	 * 判断在同一目录下是否有同名文件
	 * @param url
	 * @param filename
	 * @return
	 */
	public static boolean fileExist(String url , String filename){
		File loc = new File(url);
		if(loc.isDirectory()){
			File[] files = loc.listFiles();
			for (File file : files) {
				if(file.getName().equals(filename)){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 判断文件是否存在
	 * @param fullPath 文件全路径
	 * @return
	 */
	public static boolean fileExist(String fullPath){
		File loc = new File(fullPath);
		return loc.exists();
	}
	public static void main(String[] args) {
		File file = new File("D://result_jkx.txt");
		System.out.println(file.getName());
	}
}
