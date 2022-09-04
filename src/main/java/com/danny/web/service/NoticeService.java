package com.danny.web.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.danny.web.entity.Notice;
import com.danny.web.entity.NoticeView;

public class NoticeService {
	
	private static DataSource dataSource;
	static{
		try {
			Context ctx = new InitialContext();
			
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleCloudATP");
			
		}catch(Exception e) {
			e.getStackTrace();
		} // try-catch
	} // static initializer
	
	public int deleteNoticeAll(int[] ids) {
		
		int result = 0;
		String params = "";
		for(int i=0;i<ids.length;i++) {
			params += ids[i];
			if(i<ids.length-1) params += ",";
		}
		String sql = "DELETE NOTICE WHERE ID IN ("+params+")";
		
		

//		String url = "jdbc:oracle:thin:@DB20220609152312_high?TNS_ADMIN=/Library/opt/OracleCloudWallet/ATP";
//		String uid = "ADMIN";
//		String pwd = "Rlarldnd5345411!";
//		String driver = "oracle.jdbc.driver.OracleDriver";

		try {
//			Class.forName(driver);
			Connection con = NoticeService.dataSource.getConnection();
			Statement st = con.createStatement();
//		PreparedStatement st = con.prepareStatement(sql);
//		st.setInt(1,id);
			
			result = st.executeUpdate(sql); 
			
			
			st.close();
			con.close();
			
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	} // deleteNoticeAll
	
	public int pubNoticeAll(int[] oids, int[] cids){
		// 문자열배열로 변환하기
		List<String> oidsList = new ArrayList<>();
		for(int i=0; i<oids.length;i++)
			oidsList.add(String.valueOf(oids[i]));
		
		List<String> cidsList = new ArrayList<>();
		for(int i=0; i<cids.length;i++)
			cidsList.add(String.valueOf(cids[i]));
		
		return pubNoticeAll(oidsList, cidsList);
	} // 일괄공개 (배열)
	
	public int pubNoticeAll(List<String> oids, List<String> cids){
		
		String oidsCSV = String.join(",", oids);
		String cidsCSV = String.join(",", cids);
		
		return pubNoticeAll(oidsCSV, cidsCSV);
	} // 일괄공개 (List)
	
	public int pubNoticeAll(String oidsCSV, String cidsCSV){
		
		int result = 0;
		
		String sqlOpen = String.format("UPDATE NOTICE SET PUB=1 WHERE ID IN (%s)",oidsCSV) ;
		String sqlClose = String.format("UPDATE NOTICE SET PUB=0 WHERE ID IN (%s)",cidsCSV) ;

//		String url = "jdbc:oracle:thin:@DB20220609152312_high?TNS_ADMIN=/Library/opt/OracleCloudWallet/ATP";
//		String uid = "ADMIN";
//		String pwd = "Rlarldnd5345411!";
//		String driver = "oracle.jdbc.driver.OracleDriver";

		try {
//			Class.forName(driver);
			Connection con = NoticeService.dataSource.getConnection();
			Statement stOpen = con.createStatement();
			result += stOpen.executeUpdate(sqlOpen); 
			
			Statement stClose = con.createStatement();
			result += stClose.executeUpdate(sqlClose);
			
			stClose.close();
			stOpen.close();
			con.close();
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	} // 일괄공개 (문자열)
	
	public int insertNotice(Notice notice){
		
		int result = 0;
		
		String sql = "INSERT INTO NOTICE(TITLE, CONTENT, WRITER_ID, PUB, FILES)"+
					" VALUES(?,?,?,?,?)";
		
		

//		String url = "jdbc:oracle:thin:@DB20220609152312_high?TNS_ADMIN=/Library/opt/OracleCloudWallet/ATP";
//		String uid = "ADMIN";
//		String pwd = "Rlarldnd5345411!";
//		String driver = "oracle.jdbc.driver.OracleDriver";

		try {
//			Class.forName(driver);
			Connection con = NoticeService.dataSource.getConnection();
//			Statement st = con.createStatement();
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, notice.getTitle());
			st.setString(2, notice.getContent());
			st.setString(3, notice.getWriterId());
			st.setBoolean(4, notice.getPub());
			st.setString(5, notice.getFiles());
			
			result = st.executeUpdate(); 
			
			
			st.close();
			con.close();
			
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	} // 공지사항추가
	public int deleteNotice(int id){
		return 0;
	} // 공지사항삭제
	public int updateNotice(Notice notice){
		return 0;
	} // 공지사항수정
	public List<Notice> getNoticeNewstList(){
		return null;
	} // index 페이지에 있는 작은 공지사항
	
	
	
	
	public List<NoticeView> getNoticeList(){
		
		return getNoticeList("title","",1);
		
	} // getNoticeList
	public List<NoticeView> getNoticeList(int page){
		
		return getNoticeList("title","",page);
		
	} // getNoticeList
	public List<NoticeView> getNoticeList(String field, String query, int page){
		List<NoticeView> list = new ArrayList<>();
		
		String sql = "SELECT * FROM ("+
				"    SELECT ROWNUM NUM, N.* "+ 
				"    FROM (SELECT * FROM NOTICE_VIEW"+
				"	 WHERE "+ field +" LIKE ? ORDER BY REGDATE DESC) N"+
				") "+
				"WHERE NUM BETWEEN ? AND ?";
			// 1, 11, 21 => 1 + (page + 1)*10
			// 10, 20, 30 => page*10
		
		
//		String url = "jdbc:oracle:thin:@DB20220609152312_high?TNS_ADMIN=/Library/opt/OracleCloudWallet/ATP";
//		String uid = "ADMIN";
//		String pwd = "Rlarldnd5345411!";
//		String driver = "oracle.jdbc.driver.OracleDriver";

		try {
//			Class.forName(driver);
			Connection con = NoticeService.dataSource.getConnection();
//			Statement st = con.createStatement();
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1,"%"+query+"%");
			st.setInt(2, 1+(page-1)*10);
			st.setInt(3, page*10);
			
			
			ResultSet rs = st.executeQuery(); 

			while(rs.next()){ 
				
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
//				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("pub");
				int cmtCount = rs.getInt("CMT_C");
				
				System.out.println(pub);
				System.out.println(pub);
				System.out.println(pub);
				System.out.println(pub);
				System.out.println(pub);
				NoticeView notice = new NoticeView(
						id,
						title,
						writerId,
						regdate,
						hit,
						files,
//						content,
						pub,
						cmtCount
						);
				list.add(notice);
			}
				
			    rs.close();
			    st.close();
			    con.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
		
	} // getNoticeList
	
	public List<NoticeView> getNoticePubList(String field, String query, int page) {
		List<NoticeView> list = new ArrayList<>();
		
		String sql = "SELECT * FROM ("+
				"    SELECT ROWNUM NUM, N.* "+ 
				"    FROM (SELECT * FROM NOTICE_VIEW"+
				"	 WHERE PUB=1 AND "+ field +" LIKE ? ORDER BY REGDATE DESC) N"+
				") "+
				"WHERE NUM BETWEEN ? AND ?";
			// 1, 11, 21 => 1 + (page + 1)*10
			// 10, 20, 30 => page*10
		
		
//		String url = "jdbc:oracle:thin:@DB20220609152312_high?TNS_ADMIN=/Library/opt/OracleCloudWallet/ATP";
//		String uid = "ADMIN";
//		String pwd = "Rlarldnd5345411!";
//		String driver = "oracle.jdbc.driver.OracleDriver";

		try {
//			Class.forName(driver);
			Connection con = NoticeService.dataSource.getConnection();
//			Statement st = con.createStatement();
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1,"%"+query+"%");
			st.setInt(2, 1+(page-1)*10);
			st.setInt(3, page*10);
			
			
			ResultSet rs = st.executeQuery(); 

			while(rs.next()){ 
				
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
//				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("pub");
				int cmtCount = rs.getInt("CMT_C");
				
				System.out.println(pub);
				System.out.println(pub);
				System.out.println(pub);
				System.out.println(pub);
				System.out.println(pub);
				NoticeView notice = new NoticeView(
						id,
						title,
						writerId,
						regdate,
						hit,
						files,
//						content,
						pub,
						cmtCount
						);
				list.add(notice);
			}
				
			    rs.close();
			    st.close();
			    con.close();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	} // getNoticepubList
	
	public int getNoticeCount() {
		
		return getNoticeCount("title","");
	}
	
	public int getNoticeCount(String field, String query) {
		
		int count = 0;
		
		String sql = "SELECT COUNT(ID) COUNT FROM ("+
				"    SELECT * FROM NOTICE"+
				"	 WHERE "+ field +" LIKE ? ORDER BY REGDATE DESC"+
				")";
		
//		String url = "jdbc:oracle:thin:@DB20220609152312_high?TNS_ADMIN=/Library/opt/OracleCloudWallet/ATP";
//		String uid = "ADMIN";
//		String pwd = "Rlarldnd5345411!";
//		String driver = "oracle.jdbc.driver.OracleDriver";

		try {
//			Class.forName(driver);
			Connection con = NoticeService.dataSource.getConnection();
//			Statement st = con.createStatement();
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1,"%"+query+"%");
			
			
			ResultSet rs = st.executeQuery(); 

			if(rs.next()) count = rs.getInt("count");
				
		    rs.close();
		    st.close();
		    con.close();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
		
	} // getNoticeCount
	
	public int getNoticePubCount(String field, String query) {
		
		int count = 0;
		
		String sql = "SELECT COUNT(ID) COUNT FROM ("+
				"    SELECT * FROM NOTICE"+
				"	 WHERE PUB=1 AND "+ field +" LIKE ? ORDER BY REGDATE DESC"+
				")";
		
//		String url = "jdbc:oracle:thin:@DB20220609152312_high?TNS_ADMIN=/Library/opt/OracleCloudWallet/ATP";
//		String uid = "ADMIN";
//		String pwd = "Rlarldnd5345411!";
//		String driver = "oracle.jdbc.driver.OracleDriver";

		try {
//			Class.forName(driver);
			Connection con = NoticeService.dataSource.getConnection();
//			Statement st = con.createStatement();
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1,"%"+query+"%");
			
			
			ResultSet rs = st.executeQuery(); 

			if(rs.next()) count = rs.getInt("count");
				
		    rs.close();
		    st.close();
		    con.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	} // getNoticePubCount
	
	public Notice getNotice(int id) {
		
		Notice notice = null;
		
		String sql ="SELECT * FROM NOTICE WHERE ID=?";
		
//		String url = "jdbc:oracle:thin:@DB20220609152312_high?TNS_ADMIN=/Library/opt/OracleCloudWallet/ATP";
//		String uid = "ADMIN";
//		String pwd = "Rlarldnd5345411!";
//		String driver = "oracle.jdbc.driver.OracleDriver";

		try {
//			Class.forName(driver);
			Connection con = NoticeService.dataSource.getConnection();
//			Statement st = con.createStatement();
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setInt(1,id);
			
			
			ResultSet rs = st.executeQuery(); 

			if(rs.next()){ 
				
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("pub");
				
				notice = new Notice(
						nid,
						title,
						writerId,
						regdate,
						hit,
						files,
						content,
						pub
						);
			} // if
				
			    rs.close();
			    st.close();
			    con.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // try-catch
		
		return notice;
	} // getNotice
	
	public Notice getNextNotice(int id) {
		
		Notice notice = null;
		
		String sql = "SELECT * FROM NOTICE "+ 
				"WHERE ID = ("+
		    	"SELECT ID FROM NOTICE "+
		    	"WHERE REGDATE > (SELECT REDATE FROM NOTICE WHERE ID = ?) "+
		    	"AND ROWNUM = 1"+
		    	");";
		
//		String url = "jdbc:oracle:thin:@DB20220609152312_high?TNS_ADMIN=/Library/opt/OracleCloudWallet/ATP";
//		String uid = "ADMIN";
//		String pwd = "Rlarldnd5345411!";
//		String driver = "oracle.jdbc.driver.OracleDriver";

		try {
//			Class.forName(driver);
			Connection con = NoticeService.dataSource.getConnection();
//			Statement st = con.createStatement();
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setInt(1,id);
			
			
			ResultSet rs = st.executeQuery(); 

			if(rs.next()){ 
				
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("pub");
				
				notice = new Notice(
						nid,
						title,
						writerId,
						regdate,
						hit,
						files,
						content,
						pub
						);
			} // if
				
			    rs.close();
			    st.close();
			    con.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // try-catch
		
		return notice;
		
	} // getNextNotice
	
	public Notice getprevNotice(int id) {
		Notice notice = null;
		
		String sql = "SELECT * FROM NOTICE "+
					"WHERE ID = ("+ 
				    "SELECT ID FROM (SELECT * FROM NOTICE ORDER BY REGDATE DESC ) "+
				    "WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID = 3) "+
				    "AND ROWNUM = 1"+
				    ");";
		
		
//		String url = "jdbc:oracle:thin:@DB20220609152312_high?TNS_ADMIN=/Library/opt/OracleCloudWallet/ATP";
//		String uid = "ADMIN";
//		String pwd = "Rlarldnd5345411!";
//		String driver = "oracle.jdbc.driver.OracleDriver";

		try {
//			Class.forName(driver);
			Connection con = NoticeService.dataSource.getConnection();
//			Statement st = con.createStatement();
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setInt(1,id);
			
			
			ResultSet rs = st.executeQuery(); 

			if(rs.next()){ 
				
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("pub");
				
				notice = new Notice(
						nid,
						title,
						writerId,
						regdate,
						hit,
						files,
						content,
						pub
						);
			} // if
				
			    rs.close();
			    st.close();
			    con.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // try-catch
		
		return notice;
	} // getPrevNotice

	
	
} // end class
