package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {

		System.out.println("HI0");
		///여기서부터 실제로 MYSQL에 접속 하게 해 주는 부분///
		try {
			String dbURL =  "jdbc:mysql://localhost:3306/BBS?serverTimezone=UTC";
			//3306 포트는 우리 컴퓨터에 설치된 MYSQL 에 접속 할 수 있게 해주는 포트
			String dbID = "root";
			String dbPassword ="rlaxogns2!";
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Mysql 드라이버를 찾게 해줌
			// Driver Mysql 을 찾게 해주는 라이브러리
			if(true) {
				System.out.println("HI1");
			}
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); 
			
			if(true) {
				System.out.println("HI2");
			}
		}catch (Exception e) {
			System.out.println("userDAO Constructor Error !! ");
			e.printStackTrace();
		}
		///여기까지 실제로 MYSQL에 접속 하게 해 주는 부분///		
		
		///
	}
		public int login(String userID, String userPassword) {
			String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
			try {
				pstmt = conn.prepareStatement(SQL);
				//어떠한 정해진 sQL문장을 DB에 삽입하는 그러한 형식으로 인스턴스를 가져오고
				pstmt.setString(1,  userID);
				//sql 인젝션 같은 해킹수단을 방지하기위해서 PreparedStatement라는걸 이용하여 하나의 문장을 미리 준비해놨다가
				// 뒤에 ? 같은걸 하나 넣어놨다가 ?의 내용에 userID를 넣어줌으로서 실제로 데이터 베이스에는 사용자의 입력 ID를 입력받아서
				// 실제 존재하는지 체크 후 password 체크
				
				rs = pstmt.executeQuery();
				//실행한 결과를 넣어줌
				if(rs.next()) {
					if(rs.getString(1).equals(userPassword) ) {
						return 1; // 로그인 성공
				}else
					return 0; // 비밀번호가 틀렸을시
				}
				return -1; // 아이디가 없다.
			}catch (Exception e) {
				System.out.println("userDAO Login Method Error !! ");
				e.printStackTrace();
			}
			return -2; // DB 에러
		}
	}

