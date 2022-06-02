package com.javaex.vo;

public class UserVo {
	public String id, pw, name, gender;
	public int no;
	
	public UserVo() {
	}
	public UserVo(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
	public UserVo(String id, String pw, String name, String gender) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.gender = gender;
	}
	public UserVo(int no, String id, String pw, String name, String gender) {
		this.no = no;
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "UserVo [id=" + id + ", pw=" + pw + ", name=" + name + ", gender=" + gender + ", no=" + no + "]";
	}
}
