package com.yufeng.test;

import java.io.IOException;

public class TestOpenWindowsDir {
	public static void main(String[] args) {
		try {  
            String[] cmd = new String[5];  
            cmd[0] = "cmd";  
            cmd[1] = "/c";  
            cmd[2] = "start";  
            cmd[3] = " ";  
            cmd[4] = "D:/J2EE ";  
            Runtime.getRuntime().exec(cmd);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
}
