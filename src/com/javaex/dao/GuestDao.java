package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestDao {
		
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
		
			String query = "create table guestbook (\r\n"
					+ "    no number(10)\r\n"
					+ "    name varchar2(80)\r\n"
					+ "    ,password varchar2(20)\r\n"
					+ "    ,content varchar2(2000)\r\n"
					+ "    ,reg_gate date\r\n"
					+ "    ,primary key(no)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.Close();
		return "guest 테이블 생성 완료";
	}
	
	public String DropTable() {
		this.getConnection();
		try {
			String query = "drop table guestbook ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.executeUpdate();
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return "guest 테이블 삭제 완료";
	}
	
	public String CreateSeq() {
		this.getConnection();
		try {
		
			String query = "create sequence seq_guestbook_no\r\n"
					+ "increment by 1\r\n"
					+ "start with 1\r\n"
					+ "nocache";
			
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.Close();
		return "guest_no 시퀀스 생성 완료";
	}
	
	public String DropSeq() {
		this.getConnection();
		try {
		
			String query = "drop sequence seq_guestbook_no\r\n";
			
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.Close();
		return "guest_no 시퀀스 삭제 완료";
	}
	
	public String Insert(GuestVo guestVo) {
		getConnection();
		try {
			String query = "insert into guestbook \r\n"
					+ "values(seq_guestbook_no.nextval, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestVo.name);
			pstmt.setString(2, guestVo.password);
			pstmt.setString(3, guestVo.content);
			pstmt.setString(4, guestVo.regDate);

			count = pstmt.executeUpdate(); 
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count + "건이 등록 되었습니다.";
	}
	
	public String DeleteAll() {
		getConnection();
		try {
			String query = "";
			query += "delete from guestbook ";

			pstmt = conn.prepareStatement(query);

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count + "건이 삭제 되었습니다.";
	}

	public String Delete(int guestNo) {
		getConnection();
		try {
			String query = "";
			query += "delete from guestbook ";
			query += "where no = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, guestNo);

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count + "건이 삭제 되었습니다.";
	}
	
	public List<GuestVo> SelectAll() {
		List<GuestVo> guestList = new ArrayList<GuestVo>();
		getConnection();
		try {
			String query = "select no\r\n"
					+ "    ,name\r\n"
					+ "    ,password\r\n"
					+ "    ,content\r\n"
					+ "    ,reg_date\r\n"
					+ "from guestbook\r\n"
					+ "order by no";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int guestNo = rs.getInt(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String content = rs.getString(4);
				String regDate = rs.getString(5);
				GuestVo guestVo = new GuestVo(guestNo, name, password, content, regDate);
				guestList.add(guestVo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return guestList;
	}
	
	public GuestVo Select(int guestNo) {
		GuestVo guestVo = null;
		getConnection();
		try {
			String query = "";
			query += "select ";
			query += "no, name, password, content, reg_date ";
			query += "from guestbook ";
			query += "order by no ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int guestNo2 = rs.getInt(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String content = rs.getString(4);
				String regDate = rs.getString(5);
				guestVo = new GuestVo(guestNo2, name, password, content, regDate);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return guestVo;
	}
}
