/* 
        Singleton Pattern을 적용 :
        1.  Singleton instance에 대한 멤버변수 private static
        2.  getInstance() : Singleton public static 메서드 제공
        3.  생성자 private
*/

package notice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *<pre>
 * Database의 Notice 정보를 접근하기위한 CRUD처리 기능을 담고있는 클래스
 *</pre>
 *
 */

@Repository
public class NoticeDao implements Dao {
	
	@Autowired
	private FactoryDao factory;
	
    /* 
        Singleton Pattern을 적용 :
        1.  Singleton instance에 대한 멤버변수 private static
        2.  getInstance() : Singleton public static 메서드 제공
        3.  생성자 private
    */
    // 1.
    private static NoticeDao instance;

    // 2.
    public static NoticeDao getInstance() {
        if (instance == null) {
        	instance = new NoticeDao();
        }
        return instance;
    }

    /**
    * Default constructor
    */
    // 3.
	private NoticeDao() {
//		System.out.println("NoticeDao default constructor");
	}

    /**
    * 공지사항 정보를 DB에 입력합니다.
    * 공지사항 입력을 위해서 기존에 저장된 공지사항의 번호 중 가장 큰값을 구합니다.
    * 가장 큰값에 1을 더해 공지사항의 번호를 구하고, 공지사항 레코드가 존재하지 않을 경우 번호를 1로 설정합니다.
    * 구해진 번호와 매개변수로 입력된 id, title, content, 그리고 오늘날짜를 구하여 Notice 테이블에 한 레코드를 추가합니다.
    * @param writer 작성자
    * @param title  제목
    * @param content  공지사항 내용
    * @return void
    */
	public int noticeInsert(Notice notice) {
		Statement stmt = null;
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			int seqnum;
			con = factory.getConnection();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT max(num) FROM notice");
			if (rs.next()) {
				seqnum = rs.getInt(1) + 1;
			} else {
				seqnum = 1;
			}
			pstmt = con.prepareStatement("INSERT INTO notice VALUES(?,?,?,?,?)");
			pstmt.setInt(1, seqnum);
			pstmt.setString(2, notice.getWriter());
			Date dt = new Date();
			SimpleDateFormat sd = new SimpleDateFormat();
			String date = sd.getDateInstance().format(dt);
			pstmt.setString(3, date);
			pstmt.setString(4, notice.getTitle());
			pstmt.setString(5, notice.getContent());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				factory.close(stmt, pstmt, con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	/**
	 * 공지사항 내용을 수정합니다
	 * @param num 글번호
	 * @param content 내용
	 */
	public int noticeUpdate(Notice notice){
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement("update notice set title=?, cont=? where num=?");
			pstmt.setString(1, notice.getTitle());
			pstmt.setString(2, notice.getContent());
			pstmt.setInt(3, notice.getNum());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { 
				factory.close(null, pstmt, con);
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
/**
* DB를 연동하여 공지사항 정보를 Notice 객체에 저장하고 객체들의 목록을 ArrayList 형태로 리턴합니다.
* @return ArrayList : 공지사항 정보 목록
*/
	public ArrayList<Notice> noticeList(){
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		ArrayList<Notice> list=new ArrayList<>();
		try {
			con = factory.getConnection();					
			String sql="select * from notice order by num";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				list.add(new Notice(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				factory.close(con, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

    /**
    * 매개변수로 전달된 번호의 공지사항 정보를 검색하여 Notice 형태로 리턴합니다.
    * @param num 검색하고자 하는 공지사항 번호
    * @return Notice : 검색된 공지사항정보(Notice)
    */
	public Notice noticeView(int num){
		Connection con = null;
		Statement stmt=null;
		Notice n=new Notice();
		try {
    		con = factory.getConnection();
			stmt = con.createStatement();
			String query = "SELECT * FROM Notice where num="+num;
			ResultSet myResult = stmt.executeQuery(query);
			if (myResult.next()) {
				n.setNum(myResult.getInt("num"));
				n.setWriter(myResult.getString("writer"));
				n.setInDate(myResult.getString("inDate"));
				n.setTitle(myResult.getString("title"));
				n.setContent(myResult.getString("cont"));
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
		return n; 
	}

    /**
    * 매개변수로 전달된 번호의 공지사항 정보를 삭제합니다.
    * @param num 삭제하고자 하는 공지사항 번호
    * @return void : 
    */
	public int noticeDelete(int num) {
		Connection con = null;
		Statement stmt=null;
        try {
    		con = factory.getConnection();
			stmt = con.createStatement();
			String query = "delete from Notice where num="+num;
			return stmt.executeUpdate(query);
        } catch (SQLException e) {
			System.out.println(e);
		} finally { 
			try { 
				factory.close(stmt, null, con);
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
        return 0;
	}
}
