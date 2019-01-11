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

		///���⼭���� ������ MYSQL�� ���� �ϰ� �� �ִ� �κ�///
		try {
			String dbURL =  "jdbc:mysql://localhost:3306/BBS?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
			//3306 ��Ʈ�� �츮 ��ǻ�Ϳ� ��ġ�� MYSQL �� ���� �� �� �ְ� ���ִ� ��Ʈ
			String dbID = "root";
			String dbPassword ="rlaxogns2!";
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Mysql ����̹��� ã�� ����
			// Driver Mysql �� ã�� ���ִ� ���̺귯��
			
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); 
			
			
		}catch (Exception e) {
			System.out.println("BbsDAO Constructor Error !! ");
			e.printStackTrace();
		}
		///������� ������ MYSQL�� ���� �ϰ� �� �ִ� �κ�///	
	}
public String getDate() {
		
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);	
			//bbsdao ���� ��쿡�� ���� �Լ����� ����ϱ⶧���� ������ �Լ����� db ���ٿ� �־ ������ �Ͼ�� ������ ���ؼ� pstmt�� �ȿ� �־���.
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1); //
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		

		return ""; // �����ͺ��̽� ����
	}

public int getNext() {
	
	String SQL = "SELECT bbsID From BBS order by bbsID DESC";
	try {
		PreparedStatement pstmt = conn.prepareStatement(SQL);	
		//bbsdao ���� ��쿡�� ���� �Լ����� ����ϱ⶧���� ������ �Լ����� db ���ٿ� �־ ������ �Ͼ�� ������ ���ؼ� pstmt�� �ȿ� �־���.
		rs=pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt(1)+1; //
		}
		return 1; // ù ��° �Խù��� ���
	}catch(Exception e) {
		e.printStackTrace();
	}
	

	return -1; // �����ͺ��̽� ����

}

	public int write(String bbsTitle, String userID, String bbsContent) {

		String SQL = "insert into BBS values (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);	
			//bbsdao ���� ��쿡�� ���� �Լ����� ����ϱ⶧���� ������ �Լ����� db ���ٿ� �־ ������ �Ͼ�� ������ ���ؼ� pstmt�� �ȿ� �־���.
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
		

		return -1; // �����ͺ��̽� ����

	}
	
	public ArrayList<Bbs> getList(int pageNumber) {

		String SQL = "select * from bbs where bbsID < ? AND bbsAvailable = 1 order by bbsID desc limit 10";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);	
			//bbsdao ���� ��쿡�� ���� �Լ����� ����ϱ⶧���� ������ �Լ����� db ���ٿ� �־ ������ �Ͼ�� ������ ���ؼ� pstmt�� �ȿ� �־���.
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
			//bbsdao ���� ��쿡�� ���� �Լ����� ����ϱ⶧���� ������ �Լ����� db ���ٿ� �־ ������ �Ͼ�� ������ ���ؼ� pstmt�� �ȿ� �־���.
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
