package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/main")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UserDao userDao = new UserDao();
		String action = request.getParameter("action");
		if (action.equals("login")) {
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.html");
		}else if (action.equals("join")) {
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.html");
		}else if (action.equals("joinOk")) {
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			UserVo userVo = new UserVo(id, pw, name, gender);
			userDao.Insert(userVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.html");
		}else if (action.equals("loginCheck")) {
			int no = Integer.parseInt(request.getParameter("no"));
			String name = request.getParameter("id");
			String pw = request.getParameter("pw");
			if( name.equals(userDao.Select(no).name)) {
				if( pw.equals(userDao.Select(no).pw)) {
					userDao.Select(no);
					WebUtil.redirect(request, response, "/mysite2/main?action=1");
				}
			}else {
				//아이디나 비밀번호를 확인하여 주십시오라는 팝업창 띄우기
			}
		}else {
			WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
