package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private ResultSet rs = null;
	private int count = 0;
	private String id = "webdb";
	private String pw = "webdb";
	
	public void getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void Close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public String CreateTable() {
		this.getConnection();
		try {
		
			String query = "create table board( "
					+ "    no number "
					+ "    ,title varchar2(500) not null "
					+ "    ,content varchar2(4000) "
					+ "    ,hit number "
					+ "    ,reg_date date not null "
					+ "    ,user_no number not null "
					+ "    ,primary key (no) "
					+ "    ,constraint board_fk foreign key (user_no) "
					+ "    references users(no) )";
			
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.Close();
		return "board 테이블 생성 완료";
	}
	
	public String DropTable() {
		this.getConnection();
		try {
			String query = "drop table board ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.executeUpdate();
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return "board 테이블 삭제 완료";
	}
	
	public String CreateSeq() {
		this.getConnection();
		try {
		
			String query = "create sequence seq_board_no "
					+ "increment by 1 "
					+ "start with 1 "
					+ "nocache ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.Close();
		return "board_no 시퀀스 생성 완료";
	}
	
	public String DropSeq() {
		this.getConnection();
		try {
		
			String query = "drop sequence seq_board_no ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.Close();
		return "board_no 시퀀스 삭제 완료";
	}
	
	public String Insert(BoardVo bVo) {
		getConnection();
		try {
			String query = "insert into board(no, title, content, reg_date, user_no) "
					+ "values(seq_board_no.nextval, ?, ?, sysdate, ?)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getUserNo());

			count = pstmt.executeUpdate(); 
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count + "건이 등록 되었습니다.";
	}
	
	public String Delete(int boardNo) {
		getConnection();
		try {
			String query = "";
			query += "delete from board ";
			query += "where no = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count + "건이 삭제 되었습니다.";
	}
	
	public List<BoardVo> SelectAll() {
		List<BoardVo> bList = new ArrayList<BoardVo>();
		getConnection();
		try {
			String query = "select no "
					+ "    ,title "
					+ "    ,content "
					+ "    ,hit "
					+ "    ,reg_date "
					+ "    ,user_no "
					+ "from board "
					+ "order by no desc";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int userNo = rs.getInt(6);
				BoardVo bVo = new BoardVo(no, title, content, hit, regDate, userNo);
				bList.add(bVo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return bList;
	}
	
	public BoardVo Select(int boardNo) {
		BoardVo bVo = null;
		getConnection();
		try {
			String query = "select no "
					+ "    ,title "
					+ "    ,content "
					+ "    ,hit "
					+ "    ,reg_date "
					+ "    ,user_no "
					+ "from board "
					+ "where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int userNo = rs.getInt(6);
				bVo = new BoardVo(no, title, content, hit, regDate, userNo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return bVo;
	}
	
	public String Update(String title, String content, int boardNo) {
		try {
			getConnection();
			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "update board "
					+ "set title = ? "
					+ "    ,content = ? "
					+ "where no = ?";

			// 바인딩
			pstmt = conn.prepareStatement(query); // 문자열을 쿼리로 만들기
			pstmt.setString(1,title);
			pstmt.setString(2, content);
			pstmt.setInt(3, boardNo);

			// 실행
			count = pstmt.executeUpdate(); // 쿼리문 실행 -->리턴값으로 성공갯수

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count + "건이 수정 되었습니다.";
	}
	public String Update(BoardVo bVo) {
		try {
			getConnection();
			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "update board "
					+ "set title = ? "
					+ "    ,content = ? "
					+ "where no = ?";

			// 바인딩
			pstmt = conn.prepareStatement(query); // 문자열을 쿼리로 만들기
			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getNo());

			// 실행
			count = pstmt.executeUpdate(); // 쿼리문 실행 -->리턴값으로 성공갯수

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count + "건이 수정 되었습니다.";
	}
	
	public String Update(int hit, int boardNo) {
		try {
			getConnection();
			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "update board "
					+ "set hit = ? "
					+ "where no = ?";

			// 바인딩
			pstmt = conn.prepareStatement(query); // 문자열을 쿼리로 만들기
			pstmt.setInt(1, hit);
			pstmt.setInt(2, boardNo);

			// 실행
			count = pstmt.executeUpdate(); // 쿼리문 실행 -->리턴값으로 성공갯수

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count + "건이 수정 되었습니다.";
	}
}
