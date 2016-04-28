package com.yufeng.service;

import java.util.List;

import com.yufeng.model.FileItem;

public interface ItemService extends SocketService {
	public List<FileItem> sender(String directory);
	public void receiver();
}
