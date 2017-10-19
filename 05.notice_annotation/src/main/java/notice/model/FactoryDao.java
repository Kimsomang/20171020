package notice.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

//@Configuration
//@PropertySource("dbserver.properties")
@Repository
public class FactoryDao {
	// singleton pattern
	private static FactoryDao instance;
	
	// beans-map.xml : constructor 인젝션 자동 주입 설정 (map)
//	private static Map dbserver;
	private String driver;
	private String url;
	private String username;
	private String password;
	
	/** default constructor */
	/*private FactoryDao() {
//		System.out.println("factoryDao default constructor");
	}
	
	public static FactoryDao getInstance() {
		if(instance == null) {
			instance = new FactoryDao();
		}
		return instance;
	}*/

	/** Map parameter 초기화 생성자 */
	/*private FactoryDao(Map dbserver) {
//		System.out.println("factoryDao constructor");
		
		driver = dbserver.get("driver").toString();
		url = dbserver.get("url").toString();
		username = dbserver.get("username").toString();
		password = dbserver.get("password").toString();
		this.dbserver = dbserver;
	}
	
	public static FactoryDao getInstance(Map dbserver) {
		if(instance == null) {
			instance = new FactoryDao(dbserver);
		}
		return instance;
	}*/
	
	@Autowired
	private FactoryDao(@Qualifier("dbserver")Properties dbserver) {
		driver = dbserver.getProperty("oracle.driver");
		url = dbserver.getProperty("oracle.url");
		username = dbserver.getProperty("oracle.userName");
		password = dbserver.getProperty("oracle.password");
	}
	
	/** DBMS 연결 객체 반환 */
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
	/** DBMS 자원해제 */
	public void close(Connection con, PreparedStatement pstmt, ResultSet rs) throws SQLException {
		if(rs != null){	rs.close(); }
		if(pstmt != null){ pstmt.close(); }
		if(con != null){ con.close(); }
	}
	
	/** DBMS 자원해제 */
	public void close(Statement stmt, PreparedStatement pstmt, Connection con) throws SQLException {
		if(stmt != null){ stmt.close(); }
		if(pstmt != null){ pstmt.close(); }
		if(con != null){ con.close(); }
	}
}
