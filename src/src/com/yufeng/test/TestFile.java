package com.yufeng.test;

import java.io.File;

public class TestFile {
	public static void main(String[] args) {
		File file = new File("/");
		for (File f : File.listRoots()) {
			System.out.println(f.getAbsolutePath());
		}
	}
}
