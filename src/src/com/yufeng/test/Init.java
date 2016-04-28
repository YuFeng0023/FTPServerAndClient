package com.yufeng.test;

public class Init {
	public static void main(String[] args) {
		Thread t1 = new Thread(){

			@Override
			public void run() {
				try {
					TestServerUploadService.main(args);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t1.setPriority(10);
		t1.start();
		Thread t2  = new Thread(){

			@Override
			public void run() {
				try {
					TestClientUploadService.main(args);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.run();
			}
		};
		t2.start();
	}
}
