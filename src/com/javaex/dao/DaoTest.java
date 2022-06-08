package com.javaex.dao;

import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestVo2;
import com.javaex.vo.UserVo;

public class DaoTest {
	public static void main(String[] args) {
		BoardDao bDao = new BoardDao();
		GuestDao2 gDao = new GuestDao2();
		UserDao uDao = new UserDao();
		GuestVo2 gVo = new GuestVo2("name", "pw", "content");
		BoardVo bVo = new BoardVo("title", "content", 1);
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
		List<GuestVo2> gList = gDao.SelectAll();
		List<BoardVo> bList = bDao.SelectAll();
		List<UserVo> uList = uDao.SelectAll();
		for(int i=0;i<gList.size();i++) {
			System.out.println(gList.get(i).toString());
			System.out.println(bList.get(i).toString());
			System.out.println(uList.get(i).toString());
		}
		BoardVo bVo2 = new BoardVo(1, "title2", "content2");
		UserVo uVo2 = new UserVo(1, "id2", "pw2", "name2", "gender2");
		System.out.println(uDao.Update(uVo2));
		System.out.println(bDao.Update(bVo2));
		List<BoardVo> bList2 = bDao.SelectAll();
		List<UserVo> uList2 = uDao.SelectAll();
		for(int i=0;i<bList2.size();i++) {
			System.out.println(bList2.get(i).toString());
			System.out.println(uList2.get(i).toString());
		}
		System.out.println(gDao.Delete(1));
		System.out.println(bDao.Delete(1));
		System.out.println(uDao.Delete(1));
	}
}
