package com.yufeng.service;

import java.io.File;

import com.yufeng.model.FileItem;

public interface DownloadService {
	/**
	 * 发送方（客户端）
	 * @param item
	 * @param file file 必须是路径（未加判断）
	 */
	public void sender(FileItem item,File file);
	/**
	 * 接收方
	 */
	public void receiver();
}
