package com.yufeng.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProtocolReader {
	protected static ReadTxtUtil util = new ReadTxtUtil();
	public static List<String> read(){
		try {
			String dir = System.getProperty("user.dir");
			String msg = util.readFile(dir + "/src/com/yufeng/config/protocol.txt");
			String[] protocols = msg.split("\n");
			return Arrays.asList(protocols);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(read());
	}
}
