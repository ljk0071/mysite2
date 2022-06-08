package com.javaex.dao;

import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestVo;
import com.javaex.vo.UserVo;

public class DaoTest {
	public static void main(String[] args) {
		BoardDao bDao = new BoardDao();
		GuestDao gDao = new GuestDao();
		UserDao uDao = new UserDao();
		BoardVo bVo = new BoardVo("title", "content", 1);
		GuestVo gVo = new GuestVo("name", "pw", "content");
		UserVo uVo = new UserVo("id", "pw", "name", "gender");
		
		System.out.println(bDao.DropSeq());
		System.out.println(bDao.DropTable());
		System.out.println(gDao.DropSeq());
		System.out.println(gDao.DropTable());
		System.out.println(uDao.DropSeq());
		System.out.println(uDao.DropTable());
		System.out.println(uDao.CreateTable());
		System.out.println(uDao.CreateSeq());
		System.out.println(bDao.CreateTable());
		System.out.println(bDao.CreateSeq());
		System.out.println(gDao.CreateTable());
		System.out.println(gDao.CreateSeq());
		System.out.println(uDao.Insert(uVo));
		System.out.println(bDao.Insert(bVo));
		System.out.println(gDao.Insert(gVo));
		List<GuestVo> gList = gDao.SelectAll();
		List<BoardVo> bList = bDao.SelectAll();
		List<UserVo> uList = uDao.SelectAll();
		for(int i=0;i<gList.size();i++) {
			System.out.println(gList.get(i).toString());
			System.out.println(bList.get(i).toString());
			System.out.println(uList.get(i).toString());
		}
	}
}
