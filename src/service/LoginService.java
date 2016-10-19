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
 * ��װ�˵�¼��ҵ���߼�
 * @author Kami 
 */
public class LoginService {
	//����û��Ϸ���
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
				JOptionPane.showMessageDialog(null, "��¼ʧ�ܣ��û������������", "����", JOptionPane.OK_OPTION);
			}
		} else {
			AdminDao ad = new AdminDao();
			if(ad.checkAdmin(username, password)) {
				new Thread(new AdminUI()).start();
				b = true;
			} else {
				JOptionPane.showMessageDialog(null, "��¼ʧ�ܣ��û������������", "����", JOptionPane.OK_OPTION);
			}
		}
		
		return b;
	}
	
	//����û���������
	public void clear(JTextField t, JPasswordField p) {
		int reset = JOptionPane.showConfirmDialog(null, "�Ƿ�����û���������", "��ʾ", JOptionPane.YES_NO_OPTION);
		if(reset == JOptionPane.YES_OPTION) {
			t.setText("");
    		p.setText("");
		} 
		return ;
	}
	
	//��ס����
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
	
	//�ж��ϴ��Ƿ��ס����
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
				
				//�ж��Ƿ���Ҫ�Զ���¼
				b = lastAutoLogin(t, p, user);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	//�ж��Ƿ�ѡ���Զ���¼
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
	
	//�ж��ϴ��Ƿ�ѡ���Զ���¼
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
