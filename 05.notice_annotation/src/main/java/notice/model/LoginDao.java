package notice.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *<pre>
 * Database의 Users 정보를 접근하기위한 CRUD처리 기능을 담고있는 클래스
 *</pre>
 *
 */

@Repository
public class LoginDao{
	@Autowired
	private FactoryDao factory;
	private static LoginDao instance;

	/**
    * Default constructor
    */
	private LoginDao() {
//		System.out.println("LoginDao default constructor");
	}
	
	/*public static LoginDao getInstance() {
		if (instance == null) {
			instance = new LoginDao();
		}
		return instance;
	}*/
	
	private LoginDao(FactoryDao factory) {
		this.factory = factory;
	}
	
	public static LoginDao getInstance(FactoryDao factory) {
		if(instance == null) {
			instance = new LoginDao(factory);
		}
		return instance;
	}
/**
* loginCheck() method는 전달된 아이디와 패스워드를 DB와 비교하여 확인후 그 결과를 boolean 타입으로 리턴합니다.
* @param id  로그인한 아이디
* @param pw  로그인한 패스워드
* @return boolean : 아이디와 패스워드가 DB에 존재하면 true, 존재하지 않으면 false를 리턴합니다.
*/	
	public boolean loginCheck(String id,String pw){
		Connection con = null;
		Statement stmt=null;
		boolean check=false;
		try {
			con = factory.getConnection();
			stmt = con.createStatement();
			String query = "Select * from users where id='"+id+"'";
			ResultSet myResult = stmt.executeQuery(query);

			if (myResult.next()) {
			    if(pw.equals(myResult.getString(2)))
						check=true;
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally { 
			try { 
				factory.close(stmt, null, con);
			}catch(SQLException e){
					e.printStackTrace();
			}
		}
		return check; 
	}
}