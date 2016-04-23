package com.yufeng.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketUtil {
	protected Socket socket ;
	protected ObjectInputStream input;
	protected ObjectOutputStream output;
	public SocketUtil(Socket aim) throws Exception{
		if(aim==null||aim.isClosed()){
			throw new Exception("Socket不能为空且不能关闭!");
		}
		this.socket = aim;
		init();
	}
	protected void init() throws IOException{
		output = new ObjectOutputStream(socket.getOutputStream());
		output.flush();
		input = new ObjectInputStream(socket.getInputStream());
	}
	/**
	 * 写对象
	 * @param obj
	 * @throws IOException
	 */
	public void writeObject(Object obj) throws IOException{
		output.writeObject(obj);
		output.flush();
	}
	/**
	 * 读对象
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Object readObject() throws ClassNotFoundException, IOException{
		return input.readObject();
	}
	/**
	 * 从输入流中读出文件，写入本地
	 * @param filename
	 * @throws IOException 
	 */
	public void writeToLocal(String filename) throws IOException{
		WriteFileUtil writer = new WriteFileUtil();
		writer.setInput(socket.getInputStream());
		writer.setFilename(filename);
		writer.writeFile();
	}
	/**
	 * 从本地读取文件写入输入流
	 * @param filename
	 * @throws IOException
	 */
	public void readFromLocal(String filename) throws IOException{
		ReadFileUtil reader = new ReadFileUtil();
		reader.setFilename(filename);
		reader.setOutput(socket.getOutputStream());
		reader.readFile();
	}
	public int readInt() throws IOException{
		return input.readInt();
	}
	public void writeInt(int i) throws IOException{
		output.writeInt(i);
	}
	/**
	 * 关闭连接
	 * @throws IOException
	 */
	public void close() throws IOException{
		input.close();
		output.close();
		socket.close();
		input = null;
		output =null;
		socket = null;
	}
}
