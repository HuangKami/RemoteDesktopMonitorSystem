package db;

import java.sql.*;

public class DB {
	private static String url = "jdbc:mysql://localhost:3306/java?"
			+ "useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private static String user = "root";
	private static String password = "123456";
	private static String className = "com.mysql.jdbc.Driver";
	
	private DB() {}
	
	static {
		try {
			Class.forName(className);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConn() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn, Statement st, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(st != null) {
					st.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(conn != null) {
						conn.close();
					}
				} catch(SQLException e) {
					e.printStackTrace();
				} 
			}
		}
	}
}
