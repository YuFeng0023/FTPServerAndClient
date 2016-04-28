package com.yufeng.model;

import java.io.Serializable;

public class FileItem implements Serializable {
	public static Integer FILE = 1;
	public static Integer DIRECTORY = 0;
	private static final long serialVersionUID = -2951582854757822242L;
	protected int id =1;//节点标识，在客户端进行标识赋值
	protected int type;//0.路径 1.文件
	protected String name;//文件/路径名称
	protected int fid;//父节点标识
	protected String absolute;//绝对路径，可以计算出来，这样写方便一点
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public FileItem() {
		super();
	}
	public String getAbsolute() {
		return absolute;
	}
	public void setAbsolute(String absolute) {
		this.absolute = absolute;
	}
	@Override
	public String toString() {
		return name;
	}
	public FileItem( int type, String name, String absolute) {
		super();
		this.type = type;
		this.name = name;
		this.absolute = absolute;
	}
	
}
