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
		UserDao uDao = new UserDao();
		String action = request.getParameter("action");
		HttpSession session = null;
		if (action.equals("login")) {
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		} else if ("join".equals(action)) {
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

		} else if ("joinOk".equals(action)) {
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			UserVo userVo = new UserVo(id, pw, name, gender);
			uDao.Insert(userVo);

			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");

		} else if ("loginCheck".equals(action)) {
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			try {
				UserVo authUser = uDao.Select(id, pw);
				if (authUser.getId().equals(id)) {
					if(authUser.getPw().equals(pw)) {
						session = request.getSession();
						session.setAttribute("authUser", authUser);
						WebUtil.redirect(request, response, "./main");
						
					}
				}
			} catch (NullPointerException e) {
				WebUtil.redirect(request, response, "./user?action=login");
			}

		} else if ("logout".equals(action)) {
			session = request.getSession();
			session.removeAttribute("authUser");
			WebUtil.redirect(request, response, "./main");

		} else if ("modify".equals(action)) {
			session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			UserVo authVo = uDao.Select(no);
			session.setAttribute("authUser", authVo);
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");

		} else if ("update".equals(action)) {
			session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			UserVo userVo = new UserVo(no, pw, name, gender);
			uDao.Update(userVo);
			session.removeAttribute("authUser");
			session.setAttribute("authUser", userVo);
			WebUtil.redirect(request, response, "./main");

		} else {
			WebUtil.redirect(request, response, "./main");
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
