package com.yufeng.view;

import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class TestJOptionPane {
	public static void main(String[] args) {
		String username = JOptionPane.showInputDialog("请输入用户名");
		if(username==null){
			System.exit(0);
		}
		JPasswordField pw = new JPasswordField();
		JOptionPane.showMessageDialog(null, pw, "Please Input your Password", JOptionPane.PLAIN_MESSAGE);
		char[] pass = pw.getPassword();
		if(pass.length==0)System.exit(0);
		System.out.println(Arrays.toString(pass));
		System.out.println(username);
	}
}
