package com.yufeng.config;

import java.io.IOException;
import com.yufeng.util.PropertyFileReader;

public class ProtocolPropertiesReader {
	public static PropertyFileReader reader;
	static{
		try {
			reader = new PropertyFileReader();
			reader.loadFile("../config/protocol.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String get(String key){
		return reader.getValue(key);
	}
	public static void main(String[] args) {
		System.out.println(get("GetFileItem"));
	}
}
