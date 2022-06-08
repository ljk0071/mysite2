package com.javaex.dao;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestVo;
import com.javaex.vo.UserVo;

public class asd {

	public static void main(String[] args) {
		BoardDao bDao = new BoardDao();
		GuestDao gDao = new GuestDao();
		UserDao uDao = new UserDao();
		BoardVo bVo = new BoardVo("title", "content", 1);
		GuestVo gVo = new GuestVo("name", "pw", "content");
		UserVo uVo = new UserVo("id", "pw", "name", "gender");
		
//		uDao.DropSeq();
		bDao.DropTable();
		uDao.DropTable();
//		bDao.DropSeq();
//		gDao.DropSeq();
//		gDao.DropTable();
//		uDao.CreateTable();
//		uDao.CreateSeq();
//		bDao.CreateTable();
//		bDao.CreateSeq();
//		gDao.CreateTable();
//		gDao.CreateSeq();
//		uDao.Insert(uVo);
//		bDao.Insert(bVo);
//		gDao.Insert(gVo);
//		List<GuestVo> gList = gDao.SelectAll();
//		List<BoardVo> bList = bDao.SelectAll();
//		List<UserVo> uList = uDao.SelectAll();
//		for(int i=0;i<gList.size();i++) {
//			System.out.println(gList.get(i).toString());
//			System.out.println(bList.get(i).toString());
//			System.out.println(uList.get(i).toString());

	}

}
