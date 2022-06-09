package com.javaex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		BoardDao bDao = new BoardDao();
		HttpSession session = null;
		
		if (action.equals("list")) {
			String title = request.getParameter("title");
			List<BoardVo> bList = bDao.SelectAll();
			if ( title == null ) {
				session = request.getSession();
				UserDao uDao = new UserDao();
				session.setAttribute("bList", bList);
				session.setAttribute("uDao", uDao);
				WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			} else {
				List<BoardVo> searchList = new ArrayList<BoardVo>();
				for(BoardVo bVo : bList) {
					if( bVo.getTitle().contains(title) ){
						searchList.add(bVo);
					}
				}
				if (searchList.size() > 0) {
					UserDao uDao = new UserDao();
					request.setAttribute("searchList", searchList);
					request.setAttribute("uDao", uDao);
					WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
				} else {
					WebUtil.redirect(request, response, "./board?action=list");
				}
				
			}

		} else if ("writeform".equals(action)) {
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");

		} else if ("write".equals(action)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			content = content.replace("\r\n", "<br>");
			int userNo = Integer.parseInt(request.getParameter("userNo"));
			BoardVo bVo = new BoardVo(title, content, userNo);
			bDao.Insert(bVo);
			WebUtil.redirect(request, response, "./board?action=list");
		} else if ("read".equals(action)) {
			try {
				int no = Integer.parseInt(request.getParameter("no"));
				int hit = Integer.parseInt(request.getParameter("hit")) + 1;
				bDao.Update(hit, no);
				BoardVo bVo = bDao.Select(no);
				request.setAttribute("bVo", bVo);
				WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			} catch (NumberFormatException e) {
				int no = Integer.parseInt(request.getParameter("no"));
				BoardVo bVo = bDao.Select(no);
				request.setAttribute("bVo", bVo);
				WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			}

		} else if ("modify".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo bVo = bDao.Select(no);
			bVo.setContent(bVo.getContent().replace("<br>", "\r\n"));
			UserDao uDao = new UserDao();
			request.setAttribute("uDao", uDao);
			request.setAttribute("bVo", bVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");

		} else if ("update".equals(action)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			content = content.replace("\r\n", "<br>");
			int boardNo = Integer.parseInt(request.getParameter("boardNo"));
			bDao.Update(title, content, boardNo);
			WebUtil.redirect(request, response, "./board?action=list");
		} else if ("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			bDao.Delete(no);
			WebUtil.redirect(request, response, "./board?action=list");
		} else {
			WebUtil.redirect(request, response, "./board?action=list");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
