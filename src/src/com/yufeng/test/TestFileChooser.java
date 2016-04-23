package com.yufeng.test;

import javax.swing.JFileChooser;

public class TestFileChooser {
	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		System.out.println(chooser.showOpenDialog(null));;
		System.out.println(chooser.getSelectedFile());
	}
}
