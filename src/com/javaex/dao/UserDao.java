package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PersonVo;
import com.javaex.vo.UserVo;

public class UserDao {
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
		
			String query = "create table users (\r\n"
					+ "    no number\r\n"
					+ "    ,id varchar2(20) unique not null\r\n"
					+ "    ,password varchar2(20) not null\r\n"
					+ "    ,name varchar2(20)\r\n"
					+ "    ,gender varchar2(10)\r\n"
					+ "    ,primary key(no)\r\n)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.Close();
		return "users 테이블 생성 완료";
	}
	
	public String DropTable() {
		this.getConnection();
		try {
			String query = "drop table users ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.executeUpdate();
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return "users 테이블 삭제 완료";
	}
	
	public String CreateSeq() {
		this.getConnection();
		try {
		
			String query = "create sequence seq_users_no\r\n"
					+ "increment by 1\r\n"
					+ "start with 1\r\n"
					+ "nocache";
			
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.Close();
		return "users_no 시퀀스 생성 완료";
	}
	
	public String DropSeq() {
		this.getConnection();
		try {
		
			String query = "drop sequence seq_users_no\r\n";
			
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.Close();
		return "users_no 시퀀스 삭제 완료";
	}
	
	public String Insert(UserVo userVo) {
		getConnection();
		try {
			String query = "insert into users \r\n"
					+ "values(seq_users_no.nextval, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPw());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

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
			query += "delete from users ";

			pstmt = conn.prepareStatement(query);

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count + "건이 삭제 되었습니다.";
	}

	public String Delete(int userNo) {
		getConnection();
		try {
			String query = "";
			query += "delete from users ";
			query += "where no = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userNo);

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count + "건이 삭제 되었습니다.";
	}
	
	public List<UserVo> SelectAll() {
		List<UserVo> userList = new ArrayList<UserVo>();
		getConnection();
		try {
			String query = "select no\r\n"
					+ "    ,id\r\n"
					+ "    ,password\r\n"
					+ "    ,name\r\n"
					+ "    ,gender\r\n"
					+ "from users\r\n"
					+ "order by no";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int userNo = rs.getInt(1);
				String id = rs.getString(2);
				String pw = rs.getString(3);
				String name = rs.getString(4);
				String gender = rs.getString(5);
				UserVo userVo = new UserVo(userNo, id, pw, name, gender);
				userList.add(userVo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return userList;
	}
	
	public UserVo Select(int userNo) {
		UserVo userVo = null;
		getConnection();
		try {
			String query = "select no\r\n"
					+ "    ,id\r\n"
					+ "    ,password\r\n"
					+ "    ,name\r\n"
					+ "    ,gender\r\n"
					+ "from users\r\n"
					+ "where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int userNo2 = rs.getInt(1);
				String id = rs.getString(2);
				String pw = rs.getString(3);
				String name = rs.getString(4);
				String gender = rs.getString(5);
				userVo = new UserVo(userNo2, id, pw, name, gender);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return userVo;
	}
	public UserVo Select(String id, String pw) {
		UserVo userVo = null;
		getConnection();
		try {
			String query = "select no\r\n"
					+ "    ,id\r\n"
					+ "    ,password\r\n"
					+ "    ,name\r\n"
					+ "    ,gender\r\n"
					+ "from users\r\n"
					+ "where id = ? "
					+ "and password = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int userNo2 = rs.getInt(1);
				String id2 = rs.getString(2);
				String pw2 = rs.getString(3);
				String name = rs.getString(4);
				String gender = rs.getString(5);
				userVo = new UserVo(userNo2, id2, pw2, name, gender);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return userVo;
	}
	public void Update(UserVo userVo) {
		try {
			getConnection();
			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += "update users ";
			query += "set name = ? ";
			query += ",password = ? ";
			query += ",gender = ? ";
			query += "where no = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query); // 문자열을 쿼리로 만들기
			pstmt.setString(1, userVo.getName());
			pstmt.setString(2, userVo.getPw());
			pstmt.setString(3, userVo.getGender());
			pstmt.setInt(4, userVo.getNo());

			// 실행
			count = pstmt.executeUpdate(); // 쿼리문 실행 -->리턴값으로 성공갯수
			System.out.println(count + "건이 수정 되었습니다.");

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
	}
	
}
