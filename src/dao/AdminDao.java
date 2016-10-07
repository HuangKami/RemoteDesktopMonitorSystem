package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DB;

public class AdminDao {
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;
	
	//检验管理员的用户名和密码
	public boolean checkAdmin(String username, String password) {
		boolean b = false;
		try {
			//sql查询语句
			String sql = "select * from admin where username=? and password=?";
			
			//创建数据库连接
			conn = DB.getConn();
			
			//创建PreparedStatement
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			
			//获得查询结果集
			rs = ps.executeQuery();
			
			//查找是否存在用户
			if(rs.next()) {
				b = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, ps, rs);
		}
		return b;
	}
	
	
	//更改管理员密码
	public boolean updatePassword(String password) {
		boolean b = false;
		try {
			conn = DB.getConn();
			
			String sql = "update admin set password = ?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, password);

			if(ps.executeUpdate() != 0) {
				b =true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, ps, rs);
		}
		return b;
 	}
	
	//注册用户
	public boolean register(String username, String password) {
		boolean b = false;
		
		try {
			conn = DB.getConn();
			String sql = "insert into user (username, password) values (?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			if(ps.executeUpdate() != 0) {
				b = true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, ps, rs);
		}
		
		return b;
	}
	
	
	
	
	
}
