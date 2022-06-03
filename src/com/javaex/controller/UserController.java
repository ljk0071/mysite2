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

/**
 * Servlet implementation class UserController
 */
@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UserDao userDao = new UserDao();
		String action = request.getParameter("action");
		HttpSession session;
		if (action.equals("login")) {
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		} else if (action.equals("join")) {
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

		} else if (action.equals("joinOk")) {
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			UserVo userVo = new UserVo(id, pw, name, gender);
			userDao.Insert(userVo);

			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");

		} else if (action.equals("loginCheck")) {
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			try {
				UserVo authUser = userDao.Select(id, pw);
				if (authUser.id.equals(id)) {
					if(authUser.pw.equals(pw)) {
						session = request.getSession();
						session.setAttribute("authUser", authUser);
						WebUtil.redirect(request, response, "./main");
					}
				}
			} catch (NullPointerException e) {
				WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			}

		} else if (action.equals("logout")) {
			session = request.getSession();
			session.removeAttribute("authUser");
			WebUtil.redirect(request, response, "./main");

		} else if (action.equals("updateform")) {
			int no = Integer.parseInt(request.getParameter("userNo"));
			request.setAttribute("no", no);
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");

		} else if (action.equals("update")) {
			int no = Integer.parseInt(request.getParameter("userNo"));
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			UserVo userVo = new UserVo(no, pw, name, gender);
			userDao.Update(userVo);
			session = request.getSession();
			session.removeAttribute("authUser");
			session.setAttribute("authUser", userVo);
			WebUtil.forward(request, response, "./main");

		} else {
			WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
