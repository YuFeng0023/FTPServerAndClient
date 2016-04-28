package com.yufeng.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yufeng.model.FileItem;
import com.yufeng.service.FileItemService;
import com.yufeng.service.ItemService;
import com.yufeng.util.FileUtil;

public class ItemServiceImpl extends BaseService implements ItemService{
	@SuppressWarnings("unchecked")
	@Override
	public List<FileItem> sender(String directory) {
		try {
			util.writeObject(directory==null?"":directory);
			List<FileItem> list = null;
			String msg = (String) util.readObject();
			if(!msg.equals("ok")) throw new RuntimeException("路径不存在");
			list = (List<FileItem>) util.readObject();
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		state = ERROR;
		return null;
	}
	@Override
	public void receiver() {
		try {
			String path = (String) util.readObject();
			if(StringUtils.isBlank(path)||FileUtil.fileExist(path)){
				util.writeObject("ok");
			}else{
				util.writeObject("fail");
			}
			List<FileItem> list=FileItemService.getFileItem(path);
			util.writeObject(list);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public Object sender(Object... args) {
		return sender((String)args[0]);
	}
	@Override
	public Object receiver(Object... args) {
		receiver();
		return null;
	}
}
