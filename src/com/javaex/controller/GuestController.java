package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

/**
 * Servlet implementation class GuestController
 */
@WebServlet("/gc")
public class GuestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		GuestDao guestDao = new GuestDao();
		List<GuestVo> guestList = guestDao.SelectAll();

		String action = request.getParameter("action");
		if (action.equals("addList")) {
			request.setAttribute("gList", guestList);
			WebUtil.forward(request, response, "WEB-INF/addList.jsp");

		} else if (action.equals("write")) {
			String name = request.getParameter("name");
			String password = request.getParameter("pw");
			String content = request.getParameter("content");
			content=content.replace("\r\n","<br>");
			String regDate = request.getParameter("regDate");

			GuestVo guestVo = new GuestVo(name, password, content, regDate);
			guestDao.Insert(guestVo);

			WebUtil.redirect(request, response, "/guestbook2/gc?action=addList");

		} else if (action.equals("delete")) {
			int no = Integer.parseInt(request.getParameter("no"));
			String pw = request.getParameter("pw");
			if( pw.equals(guestDao.Select(no).password)) {
				guestDao.Delete(no);
			}
			
			WebUtil.redirect(request, response, "/guestbook2/gc?action=addList");
			

		} else if (action.equals("deleteform")) {
			WebUtil.forward(request, response, "WEB-INF/deleteForm.jsp");
		} else {
			System.out.println("action파라미터 없음");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
