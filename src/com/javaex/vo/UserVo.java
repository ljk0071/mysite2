package com.javaex.vo;

public class UserVo {
	private String id, pw, name, gender;
	private int no;
	
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
	public UserVo(int no, String pw, String name, String gender) {
		this.no = no;
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
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	@Override
	public String toString() {
		return "UserVo [id=" + id + ", pw=" + pw + ", name=" + name + ", gender=" + gender + ", no=" + no + "]";
	}
}
