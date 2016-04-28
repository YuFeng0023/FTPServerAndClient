package com.yufeng.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.yufeng.service.MessageService;

public class MessageServiceImpl extends BaseService implements MessageService{
	protected String[] msgs;
	/**
	 * 传递数据，第一个参数为要传递信息的个数，之后的参数为具体信息
	 * @throws IOException 
	 */
	public int send(InputStream input, OutputStream output, Object... args) throws IOException {
		if(input==null||output==null){
			throw new RuntimeException("输入输出流未赋初值！");
		}
		Integer count = (Integer) args[0];
		if(args.length!=count+1){
			throw new RuntimeException("参数错误");
		}
		ObjectOutputStream objectOut = new ObjectOutputStream(output);
		objectOut.writeInt(count);
		objectOut.flush();
		for (int i = 1; i < args.length; i++) {
			System.out.println(i);
			objectOut.writeObject(args[i]);
			objectOut.flush();
		}
		
		return 0;
	}
	/**
	 * 
	 * @param input
	 * @param output
	 * @param args
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public int recieve(InputStream input, OutputStream output, Object... args) throws IOException, ClassNotFoundException {
		if(input==null||output==null){
			throw new RuntimeException("输入输出流未赋初值！");
		}
		ObjectInputStream objectIn = new ObjectInputStream(input);
		Integer count = objectIn.readInt();
		msgs = new String[count];
		for (int i = 0; i < count; i++) {
			msgs[i]=(String) objectIn.readObject();
		}
		return 0;
	}
	public String[] getMsgs() {
		return msgs;
	}
	public void setMsgs(String[] msgs) {
		this.msgs = msgs;
	}

	@Override
	public Object sender(Object... args) {
		try {
			send(socket.getInputStream(), socket.getOutputStream(), args);
			state = SUCCESS;
		} catch (IOException e) {
			e.printStackTrace();
			state = ERROR;
			message = e.getMessage();
		}
		return null;
	}
	@Override
	public Object receiver(Object... args) {
		try {
			recieve(socket.getInputStream(), socket.getOutputStream(), args);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			state = SUCCESS;
		} catch (IOException e) {
			e.printStackTrace();
			state = ERROR;
			message = e.getMessage();
		}
		return getMsgs();
	}
	@Override
	public void send(String[] msgs) {
		Object objs[] = new Object[msgs.length+1];
		objs[0] = msgs.length;
		for (int i = 1; i < objs.length; i++) {
			objs[i] = msgs[i-1];
		}
		try {
			send(socket.getInputStream(), socket.getOutputStream(), objs);
			state =  SUCCESS;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			state = ERROR;
			message = e.getMessage();
		}
	}
	@Override
	public Object receiver() {
		try {
			recieve(socket.getInputStream(), socket.getOutputStream() );
			state =  SUCCESS;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			state = ERROR;
			message = e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			state = ERROR;
			message = e.getMessage();
		}
		return getMsgs();
	}
	
}
