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
			WebUtil.forward(request, response, "WEB-INF/views/guestbook/addList.jsp");

		} else if ("write".equals(action)) {
			String name = request.getParameter("name");
			String password = request.getParameter("pw");
			String content = request.getParameter("content");
			content=content.replace("\r\n","<br>");

			GuestVo guestVo = new GuestVo(name, password, content);
			guestDao.Insert(guestVo);

			WebUtil.redirect(request, response, "./gc?action=addList");

		} else if (action.equals("deleteform")) {
			WebUtil.forward(request, response, "WEB-INF/views/guestbook/deleteForm.jsp");
			
		} else if ("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			String pw = request.getParameter("pw");
			if( pw.equals(guestDao.Select(no).password)) {
				guestDao.Delete(no);
				WebUtil.redirect(request, response, "./gc?action=addList");
			} else {
				System.out.println("비밀번호를 확인해 주세요");
				WebUtil.redirect(request, response, request.getHeader("Referer"));
			}
			
			

		}else {
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
