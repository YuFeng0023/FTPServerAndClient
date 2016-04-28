package com.yufeng.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;


public class ReadTxtUtil {
	public String readFile(String filename) throws IOException{
		File file = new File(filename);
		StringBuilder builder = new StringBuilder();
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String temp = null;
		while(( temp = br.readLine() )!=null){
			builder.append(temp);
			builder.append("\n");
		}
		return builder.toString();
	}
	@Test
	public void test(){
		try {
			System.out.println(readFile("src/com/yufeng/config/protocol.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
