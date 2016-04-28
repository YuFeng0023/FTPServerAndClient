package com.yufeng.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
/**
 * 从本地读文件写入输出流
 */
public class ReadFileUtil {
	protected OutputStream output;//输入流
	protected String filename;//文件全路径
	protected int size=1024;//每次读写字节数
	public void setOutput(OutputStream output) {
		this.output = output;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void readFile() throws IOException{
		if(output==null||StringUtils.isBlank(filename)){
			throw new RuntimeException("未赋初值");
		}
		File file = new File(filename);
		FileInputStream fin = new FileInputStream(file);
		byte[] bytes = new byte[size];
		int flag  = 0;
		byte fileLength[] = ByteUtil.longToByte(file.length());
		output.write(fileLength);
		while((flag = fin.read(bytes))>0 ){
			output.write(bytes);
			output.flush();
		}
		fin.close();
	}
}
