package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		UserDao userDao = new UserDao();
		UserVo userVo = new UserVo();
		HttpSession session;
		if (action.equals("list")) {
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

		} else if (action.equals("writeform")) {
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");

		} else if (action.equals("write")) {
			
		} else if (action.equals("delete")) {
			int no = Integer.parseInt(request.getParameter("no"));
			String pw = request.getParameter("pw");
			if( pw.equals(userDao.Select(no).pw)) {
				userDao.Delete(no);
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
