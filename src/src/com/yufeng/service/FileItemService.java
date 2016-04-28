package com.yufeng.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yufeng.model.FileItem;
/**
 * FileItem的一个工具类
 * @author Feng
 *
 */
public class FileItemService {
	private final static int ROOT=1;
	private final static int OTHER=2;
	/**
	 * 获得某个路径下的文件信息
	 * @param dir
	 * @return
	 */
	public static List<FileItem> getFileItem(String dir){
		List<FileItem> result = null;
		if(StringUtils.isBlank(dir)){
			result = new ArrayList<>();
			for (File f : File.listRoots()) {
				result.add(toItem(f,ROOT));
			}
		}else{
			File file = new File(dir);
			if(file.exists()&&file.isDirectory()){
				result = new ArrayList<>();
				for (File f : file.listFiles()) {
					if(f.isHidden()){
						continue;
					}
					result.add(toItem(f,OTHER));
				}
			}else{
				throw new RuntimeException("传入错误路径！");
			}
		}
		return result;
	}
	/**
	 * 将文件信息记录在FileItem中，id、fid在客户端中设定
	 * @param file
	 * @return
	 */
	public static FileItem toItem(File file,int type){
		if(file==null){
			return null;
		}else{
			FileItem item = new FileItem();
			if(type==ROOT){
				item.setName(file.getAbsolutePath());
			}else{
				item.setName(file.getName());
			}
			item.setType(file.isDirectory()?0:1);
			item.setAbsolute(file.getAbsolutePath());
			return item;
		}
	}
	public static void main(String[] args) {
		System.out.println(getFileItem("D:\\testUtil.txt"));
	}
}
