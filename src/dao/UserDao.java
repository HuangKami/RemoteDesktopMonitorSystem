package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.User;
import db.DB;

public class UserDao {
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;
	
	//�������Ա���û���������
	public boolean checkUser(String username, String password) {
		boolean b = false;
		try {
			//sql��ѯ���
			String sql = "select * from user where username=? and password=?";
			
			//�������ݿ�����
			conn = DB.getConn();
			
			//����PreparedStatement
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			
			//��ò�ѯ�����
			rs = ps.executeQuery();
			
			//�����Ƿ�����û�
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
	
	//��ѯ�û�
	public User findUser(String username) {
		User user = null;
		try {
			conn = DB.getConn();
			String sql = "select * from user where username = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				user = new User();
				user.setID(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, ps, rs);
		}
		return user;
	}
	
	//�޸��û���Ϣ
	public boolean updateUser(String username, String password, int id) {
		boolean b = false;
		
		try {
			conn = DB.getConn();
			String sql = "update user set username = ?,password = ? where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setInt(3, id);
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