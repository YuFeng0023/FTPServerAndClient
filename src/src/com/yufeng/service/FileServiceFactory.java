package com.yufeng.service;

import java.io.IOException;

import com.yufeng.util.PropertyFileReader;

public class FileServiceFactory {
	static PropertyFileReader reader;
	static{
		try {
			reader.loadFile(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static SocketService createInstance(String protocolName) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		SocketService result = (SocketService) Class.forName(reader.getValue(protocolName)).newInstance();
		return result;
	}
}
