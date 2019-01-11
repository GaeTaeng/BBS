package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {

	private Connection conn;
	private ResultSet rs;
	
	public BbsDAO() {

		///여기서부터 실제로 MYSQL에 접속 하게 해 주는 부분///
		try {
			String dbURL =  "jdbc:mysql://localhost:3306/BBS?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
			//3306 포트는 우리 컴퓨터에 설치된 MYSQL 에 접속 할 수 있게 해주는 포트
			String dbID = "root";
			String dbPassword ="rlaxogns2!";
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Mysql 드라이버를 찾게 해줌
			// Driver Mysql 을 찾게 해주는 라이브러리
			
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); 
			
			
		}catch (Exception e) {
			System.out.println("BbsDAO Constructor Error !! ");
			e.printStackTrace();
		}
		///여기까지 실제로 MYSQL에 접속 하게 해 주는 부분///	
	}
public String getDate() {
		
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);	
			//bbsdao 같은 경우에는 여러 함수들을 사용하기때문에 각각의 함수끼리 db 접근에 있어서 마찰이 일어나지 ㅇ낳기 위해서 pstmt를 안에 넣어줌.
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1); //
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		

		return ""; // 데이터베이스 오류
	}

public int getNext() {
	
	String SQL = "SELECT bbsID From BBS order by bbsID DESC";
	try {
		PreparedStatement pstmt = conn.prepareStatement(SQL);	
		//bbsdao 같은 경우에는 여러 함수들을 사용하기때문에 각각의 함수끼리 db 접근에 있어서 마찰이 일어나지 ㅇ낳기 위해서 pstmt를 안에 넣어줌.
		rs=pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt(1)+1; //
		}
		return 1; // 첫 번째 게시물인 경우
	}catch(Exception e) {
		e.printStackTrace();
	}
	

	return -1; // 데이터베이스 오류

}

	public int write(String bbsTitle, String userID, String bbsContent) {

		String SQL = "insert into BBS values (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);	
			//bbsdao 같은 경우에는 여러 함수들을 사용하기때문에 각각의 함수끼리 db 접근에 있어서 마찰이 일어나지 ㅇ낳기 위해서 pstmt를 안에 넣어줌.
			pstmt.setInt(1,  getNext());
			pstmt.setString(2,  bbsTitle);
			pstmt.setString(3,  userID);
			pstmt.setString(4,  getDate());
			pstmt.setString(5,  bbsContent);
			pstmt.setInt(6,  1);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		

		return -1; // 데이터베이스 오류

	}
	
	public ArrayList<Bbs> getList(int pageNumber) {

		String SQL = "select * from bbs where bbsID < ? AND bbsAvailable = 1 order by bbsID desc limit 10";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);	
			//bbsdao 같은 경우에는 여러 함수들을 사용하기때문에 각각의 함수끼리 db 접근에 있어서 마찰이 일어나지 ㅇ낳기 위해서 pstmt를 안에 넣어줌.
			pstmt.setInt(1,  getNext() - (pageNumber -1 ) *10 ); 
			rs = pstmt.executeQuery(); 
			while(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Here BbsDAO !! :: " + list.size());

		return list;

	}
	
	public boolean nextPage(int pageNumber) {

		String SQL = "select * from bbs where bbsID < ? AND bbsAvailable = 1 order by bbsID desc limit 10";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);	
			//bbsdao 같은 경우에는 여러 함수들을 사용하기때문에 각각의 함수끼리 db 접근에 있어서 마찰이 일어나지 ㅇ낳기 위해서 pstmt를 안에 넣어줌.
			pstmt.setInt(1,  getNext() - (pageNumber -1 ) *10 ); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		

		return false; 
	}
	
}
