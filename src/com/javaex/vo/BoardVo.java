package com.javaex.vo;

public class BoardVo {
	private String title, content, regDate;
	private int no,hit,userNo;
	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
	}
	public BoardVo(String title,String content,String regDate,int userNo) {
		this.title = title;
		this.content = content;
		this.regDate = regDate;
		this.userNo = userNo;
	}
	public BoardVo(String title,String content,int userNo) {
		this.title = title;
		this.content = content;
		this.userNo = userNo;
	}
	public BoardVo(int no, String title,String content) {
		this.no = no;
		this.title = title;
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	@Override
	public String toString() {
		return "BoardVo [title=" + title + ", content=" + content + ", regDate=" + regDate + ", no=" + no + ", hit="
				+ hit + ", userNo=" + userNo + "]";
	}
	
}
