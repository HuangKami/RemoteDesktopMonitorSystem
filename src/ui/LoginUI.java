package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;

import service.LoginService;

/*
 * 用户登录界面
 * @author Kami
 */
public class LoginUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel imageLogo;
	private JTextField username;
	private JPasswordField password;
	private JRadioButton user;
	private JRadioButton admin;
	private JCheckBox rememberPass;
	private JCheckBox autoLogin;
	private JButton loginButton;
	private JButton resetButton;
	
	//获得业务逻辑实例
	LoginService service = new LoginService();
	
	public LoginUI() {
		initUI();
	}


	private void initUI() {
		//设置标题
		this.setTitle("登录界面");
		
		//设置LOGO
		URL image = LoginUI.class.getClassLoader().getResource("header.jpg");
		imageLogo = new JLabel(new ImageIcon(image));
		this.add(imageLogo, BorderLayout.NORTH);
		
		//设置头像
		URL image2 = LoginUI.class.getClassLoader().getResource("icon.jpg");
		JLabel icon = new JLabel(new ImageIcon(image2));
		icon.setBounds(55, 100, 70, 70);
		this.add(icon);
		
		//用户名和密码  
        JPanel jp = new JPanel();  
      
        JPanel jpAccount = new JPanel();  
        jpAccount.add(new JLabel("                帐号"));  
        username = new JTextField(12);  
        jpAccount.add(username);  
        jp.add(jpAccount);  
        jp.add(Box.createHorizontalStrut(1000)); 
        JPanel jpPass = new JPanel();  
        jpPass.add(new JLabel("                密码"));  
        password = new JPasswordField(12);  
        jpPass.add(password);  
        jp.add(jpPass);  
        
        jp.add(Box.createHorizontalStrut(1000));
        
        //登录用户类型
        JPanel jpType = new JPanel();
        user = new JRadioButton("用户");
        admin = new JRadioButton("管理员");
        user.setSelected(true);
        jpType.add(user);
        jpType.add(admin);
        jp.add(jpType);
        
        
        //登录设置  
        JPanel jpstatus = new JPanel(); 
        rememberPass = new JCheckBox("记住密码");
        rememberPass.setSelected(true);
        autoLogin = new JCheckBox("自动登录");
        jpstatus.add(rememberPass);  
        jpstatus.add(autoLogin);  
        jp.add(jpstatus);  
        
        //背景颜色
        jpAccount.setBackground(new Color(255, 255, 220));
        jpPass.setBackground(new Color(255, 255, 220));
        jpType.setBackground(new Color(255, 255, 220));
        jpstatus.setBackground(new Color(255, 255, 220));
        jp.setBackground(new Color(255, 255, 220));
        

        //登录按钮  
        loginButton = new JButton("登录");
        resetButton = new JButton("重置");
        jp.add(Box.createHorizontalStrut(1000));
        jp.add(loginButton);
        jp.add(resetButton);
       
        this.add(jp);  
     
        //添加监听器
        loginButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		//判断用户名和密码是否匹配
        		if(service.checkUser(username, password, user)) {
        			//登录成功后检查是否勾选记住密码
        			service.rememberPass(username, password, rememberPass);
        			
        			//登录成功后检查是否勾选自动登录
        			service.autoLogin(autoLogin);
        			
        			//登录成功关闭登录窗口
        			setVisible(false);
        		}
        	}
        });
        
        resetButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		//清空用户名和密码
        		service.clear(username, password);
        	}
        });
        
        //单选框的选择
        user.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		user.setSelected(true);
        		admin.setSelected(false);
        	}
        });
        
        //单选框的选择
        admin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		user.setSelected(false);
        		admin.setSelected(true);
        	}
        });
        
        //设置窗体属性
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 290);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
       
        //判断上次登录是否记住密码和选中自动登录
        if(!service.lastRememberPass(username, password, autoLogin, user)) {
        	this.setVisible(true);
        }
        
    }  
	
	
	public static void main(String[] args) {
		new LoginUI();
	}
	
}
