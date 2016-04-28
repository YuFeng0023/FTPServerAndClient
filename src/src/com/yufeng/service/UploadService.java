package com.yufeng.service;

import java.io.File;

import com.yufeng.model.FileItem;

public interface UploadService extends SocketService{
	public void sender(FileItem item,File file);
	public void receiver();
}
