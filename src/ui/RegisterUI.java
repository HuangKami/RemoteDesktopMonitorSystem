package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import service.RegisterService;

public class RegisterUI extends JFrame {
private static final long serialVersionUID = 1L;
	
	private JLabel imageLogo;
	private JTextField username;
	private JPasswordField password;
	private JButton registerButton;
	
	//获得业务逻辑实例
	RegisterService service = new RegisterService();
	
	public RegisterUI() {
		initUI();
	}

	private void initUI() {
		//设置标题
		this.setTitle("注册界面");
		
		//设置LOGO
		URL image = LoginUI.class.getClassLoader().getResource("header.jpg");
		imageLogo = new JLabel(new ImageIcon(image));
		this.add(imageLogo, BorderLayout.NORTH);
		
		
		//用户名和密码  
        JPanel jp = new JPanel();  
      
        JPanel jpAccount = new JPanel();  
        jpAccount.add(new JLabel("             帐号"));  
        username = new JTextField(12);  
        jpAccount.add(username);  
        jp.add(jpAccount);  
        jp.add(Box.createHorizontalStrut(1000)); 
        JPanel jpPass = new JPanel();  
        jpPass.add(new JLabel("             密码"));  
        password = new JPasswordField(12);  
        jpPass.add(password);  
        jp.add(jpPass);  
        
        jp.add(Box.createHorizontalStrut(1000));
        
        
        //背景颜色
        jpAccount.setBackground(new Color(255, 255, 220));
        jpPass.setBackground(new Color(255, 255, 220));
        jp.setBackground(new Color(255, 255, 220));
        

        //登录按钮  
        registerButton = new JButton("注册");
        jp.add(Box.createHorizontalStrut(1000));
        jp.add(registerButton);
       
        this.add(jp);  
     
        //添加监听器
        registerButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		String usernameS = username.getText().trim();
        		String passwordS = String.valueOf(password.getPassword());
        		
        		if(service.register(usernameS, passwordS)) {
        			JOptionPane.showMessageDialog(null, "注册成功", "提示", JOptionPane.OK_OPTION);
        		} else {
        			JOptionPane.showMessageDialog(null, "注册失败", "警告", JOptionPane.OK_OPTION);
        		}
        	}
        });
        
        
        //设置窗体属性
        this.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				setVisible(false);
			}
		});
        this.setSize(400, 290);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }  
}
