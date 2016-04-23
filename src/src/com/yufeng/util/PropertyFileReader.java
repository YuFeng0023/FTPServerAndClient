package com.yufeng.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 高扬
 * @version 1.0.0
 * 2015-12-21 下午10:22:19
 */
public class PropertyFileReader {
	private String defaultURL="./conf.properties";
	private InputStream in=null;
	protected Properties aim = new Properties();
	/**
	 * 加载文件
	 * @param defaultURL
	 * @throws IOException
	 * @author 高扬
	 * 2015-12-23 上午11:13:02
	 */
	public void loadFile(String defaultURL) throws IOException{
		if(in!=null){
			close();
		}
		if(defaultURL==null){
			defaultURL=this.defaultURL;
		}
		in =PropertyFileReader.class.getResourceAsStream(defaultURL);//不支持绝对路径？？
		aim.load(in);
		this.defaultURL=defaultURL;
	}
	public String getValue(String key){
		return aim.getProperty(key);
	}
	/**
	 * 关闭文件流
	 * @author 高扬
	 * 2015-12-23 上午11:08:02
	 */
	public void close(){
		if(in!=null){
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws IOException {
		PropertyFileReader p = new PropertyFileReader();
		p.loadFile("../config/config.properties");
		System.out.println(p.getValue("username"));
	}
}
