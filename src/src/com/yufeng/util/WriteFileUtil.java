package com.yufeng.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
/**
 * 全路径，将文件输出，不做任何判断（写到本地）
 */
public class WriteFileUtil {
	protected InputStream input;//输入流
	protected String filename;//文件全路径
	protected int size=1024;//每次读写字节数
	public void setInput(InputStream input) {
		this.input = input;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void writeFile() throws IOException{
		if(input==null||StringUtils.isBlank(filename)){
			throw new RuntimeException("未赋初值");
		}
		FileOutputStream fout = new FileOutputStream(new File(filename));
		byte[] bytes = new byte[size];
		int flag  = 0;
		long total ,count=0;
		byte fileLength[] = new byte[8];
		input.read(fileLength);
		total=ByteUtil.byteToLong(fileLength);
		while(count<total&&(flag = input.read(bytes))>0){
			fout.write(bytes);
			fout.flush();
			count+=flag;
		}
		fout.close();
	}
}
