package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.javaex.vo.PersonVo;

public class PhoneDao {

	private String id = "phonedb";
	private String pw = "phonedb";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private ResultSet rs = null;
	private int count = 0;

	public void getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
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

	public String Create() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			getConnection();
			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += "create table person(\r\n" + "    person_id number(5)\r\n" + "    ,name varchar2(30) not null\r\n"
					+ "    ,hp varchar2(20)\r\n" + "    ,company varchar2(20)\r\n" + "    ,primary key(person_id)\r\n)";
			System.out.println(query);

			// 바인딩
			pstmt = conn.prepareStatement(query); // 문자열을 쿼리로 만들기

			// 실행
			pstmt.executeUpdate();
			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return "Person테이블 생성 완료";
	}

	public String Drop() {
		try {
			getConnection();

			// SQL문 준비
			String query = "";
			query += "drop table person ";

			// 바인딩
			pstmt = conn.prepareStatement(query); // 문자열을 쿼리로 만들기

			// 실행
			pstmt.executeUpdate();
			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return "Person테이블 삭제 완료";
	}

	public String CreateSeq() {
		try {
			getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += ("create sequence seq_person_id\r\n"
					+ "increment by 1\r\n"
					+ "start with 1\r\n"
					+ "nocache");

			// 바인딩
			pstmt = conn.prepareStatement(query); // 문자열을 쿼리로 만들기

			// 실행
			pstmt.executeUpdate();
			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return "Person_id시퀀스 생성 완료";
	}

	public String DropSeq() {
		try {
			getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += "drop sequence seq_person_id ";

			// 바인딩
			pstmt = conn.prepareStatement(query); // 문자열을 쿼리로 만들기

			// 실행
			pstmt.executeUpdate();
			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return "Person_id시퀀스 삭제 완료";
	}

	public int Insert(PersonVo personVo) {
		try {
			getConnection();
			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += "insert into person ";
			query += "values(seq_person_id.nextval, ?, ?, ?) ";

			// 바인딩
			pstmt = conn.prepareStatement(query); // 문자열을 쿼리로 만들기
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());

			// 실행
			count = pstmt.executeUpdate(); // 쿼리문 실행 -->리턴값으로 성공갯수
			System.out.println(count + "건이 등록 되었습니다.");

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count;
	}

	public int DeleteAll() {
		try {
			getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += "delete from person ";

			// 바인딩
			pstmt = conn.prepareStatement(query); // 문자열을 쿼리로 만들기

			// 실행
			count = pstmt.executeUpdate(); // 쿼리문 실행 -->리턴값으로 성공갯수
			System.out.println(count + "건이 삭제 되었습니다.");

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count;
	}

	public int Delete(int personId) {
		try {
			getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += "delete from person ";
			query += "where person_id = ?";

			// 바인딩
			pstmt = conn.prepareStatement(query); // 문자열을 쿼리로 만들기
			pstmt.setInt(1, personId);

			// 실행
			count = pstmt.executeUpdate(); // 쿼리문 실행 -->리턴값으로 성공갯수
			System.out.println(count + "건이 삭제 되었습니다.");

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count;
	}

	public List<PersonVo> SelectAll() {
		List<PersonVo> personList = new ArrayList<PersonVo>();
		try {
			getConnection();

			String query = "";
			query += "select ";
			query += "person_id, name, hp, company ";
			query += "from person ";
			query += "order by person_id ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int personId = rs.getInt(1);
				String name = rs.getString(2);
				String hp = rs.getString(3);
				String company = rs.getString(4);
				PersonVo personVo = new PersonVo(personId, name, hp, company);
				personList.add(personVo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return personList;
	}
	
	public PersonVo Select(int personId) {
		PersonVo personVo = new PersonVo();
		try {
			getConnection();

			String query = "";
			query += "select ";
			query += "person_id, name, hp, company ";
			query += "from person ";
			query += "where person_id = ? ";
			query += "order by person_id ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int personId2 = rs.getInt(1);
				String name = rs.getString(2);
				String hp = rs.getString(3);
				String company = rs.getString(4);
				personVo = new PersonVo(personId2, name, hp, company);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return personVo;
	}

	public int Update(PersonVo personVo) {
		try {
			getConnection();
			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += "update person ";
			query += "set name = ? ";
			query += ",hp = ? ";
			query += ",company = ? ";
			query += "where person_id = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query); // 문자열을 쿼리로 만들기
			pstmt.setString(1, personVo.name);
			pstmt.setString(2, personVo.hp);
			pstmt.setString(3, personVo.company);
			pstmt.setInt(4, personVo.personId);

			// 실행
			count = pstmt.executeUpdate(); // 쿼리문 실행 -->리턴값으로 성공갯수
			System.out.println(count + "건이 수정 되었습니다.");

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
		return count;
	}

	public void Search(String str) {
		List<PersonVo> personList = new ArrayList<PersonVo>();
		try {
			getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += "select ";
			query += "person_id, name, hp, company";
			query += "from person ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int personId = rs.getInt(1);
				String name = rs.getString(2);
				String hp = rs.getString(3);
				String company = rs.getString(4);
				PersonVo personVo = new PersonVo(personId, name, hp, company);
				personList.add(personVo);
			}

			for (int i = 0; i < personList.size(); i++) {
				if (personList.get(i).toString().contains(str)) {
					System.out.println(personList.get(i).toString());
				}
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
	}

	public void Search(int num) {
		List<PersonVo> personList = new ArrayList<PersonVo>();
		try {
			getConnection();
			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += "select ";
			query += "person_id, name, hp, company";
			query += "from person ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int personId = rs.getInt(1);
				String name = rs.getString(2);
				String hp = rs.getString(3);
				String company = rs.getString(4);
				PersonVo personVo = new PersonVo(personId, name, hp, company);
				personList.add(personVo);
			}
			String num2 = num + "";
			for (int i = 0; i < personList.size(); i++) {
				if (personList.get(i).toString().contains(num2)) {
					System.out.println(personList.get(i).toString());
				}
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		Close();
	}
}
