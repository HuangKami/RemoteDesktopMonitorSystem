package service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ui.AdminUI;
import ui.UserUI;
import dao.AdminDao;
import dao.UserDao;

/*
 * 封装了登录的业务逻辑
 * @author Kami 
 */
public class LoginService {
	//检查用户合法性
	public boolean checkUser(JTextField t, JPasswordField p, JRadioButton user) {
		boolean b = false;
		String username = t.getText().trim();
		String password = String.valueOf(p.getPassword());
		
		if(user.isSelected()) {
			UserDao ud = new UserDao();
			if(ud.checkUser(username, password)) {
				new Thread(new UserUI(username)).start();
				b = true;
			} else {
				JOptionPane.showMessageDialog(null, "登录失败：用户名或密码错误", "警告", JOptionPane.OK_OPTION);
			}
		} else {
			AdminDao ad = new AdminDao();
			if(ad.checkAdmin(username, password)) {
				new Thread(new AdminUI()).start();
				b = true;
			} else {
				JOptionPane.showMessageDialog(null, "登录失败：用户名或密码错误", "警告", JOptionPane.OK_OPTION);
			}
		}
		
		return b;
	}
	
	//清空用户名和密码
	public void clear(JTextField t, JPasswordField p) {
		int reset = JOptionPane.showConfirmDialog(null, "是否清空用户名和密码", "提示", JOptionPane.YES_NO_OPTION);
		if(reset == JOptionPane.YES_OPTION) {
			t.setText("");
    		p.setText("");
		} 
		return ;
	}
	
	//记住密码
	public void rememberPass(JTextField t, JPasswordField p, JCheckBox c) {
		Properties prop = new Properties();
		try {
			FileOutputStream out = new FileOutputStream("values.properties", true);
			
			if(c.isSelected()) {
				prop.setProperty("username", t.getText());
				prop.setProperty("password", String.valueOf(p.getPassword()));
	            prop.store(out, "The New properties file");
	            out.close();
			} else {
				prop.setProperty("username", "");
				prop.setProperty("password", "");
	            prop.store(out, "The New properties file");
	            out.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//判断上次是否记住密码
	public boolean lastRememberPass(JTextField t, JPasswordField p, JCheckBox c, JRadioButton user) {
		boolean b = false;
		try {
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream("values.properties");
			prop.load(in);
		
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			
			in.close();
		
			if(!"".equals(username)) {
				t.setText(username);
				p.setText(password);
				
				//判断是否需要自动登录
				b = lastAutoLogin(t, p, user);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	//判断是否选中自动登录
	public void autoLogin(JCheckBox c) {
		Properties prop = new Properties();
		try {
			FileOutputStream out = new FileOutputStream("values.properties", true);
			
			if(c.isSelected()) {
				prop.setProperty("login", "true");
	            prop.store(out, "The New properties file");
	            out.close();
			} else {
				prop.setProperty("login", "");
	            prop.store(out, "The New properties file");
	            out.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//判断上次是否选中自动登录
	public boolean lastAutoLogin(JTextField t, JPasswordField p, JRadioButton user) {
		boolean b = false;
		try {
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream("values.properties");
			FileOutputStream out = new FileOutputStream("values.properties", true);
			prop.load(in);
			
			String login = prop.getProperty("login");
			
			in.close();
			
			if("true".equals(login)) {
				checkUser(t, p, user);
				prop.setProperty("login", "false");
	            prop.store(out, "The New properties file");
				b = true;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
