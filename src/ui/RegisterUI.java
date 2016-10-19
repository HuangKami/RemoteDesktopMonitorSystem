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
	
	//���ҵ���߼�ʵ��
	RegisterService service = new RegisterService();
	
	public RegisterUI() {
		initUI();
	}

	private void initUI() {
		//���ñ���
		this.setTitle("ע�����");
		
		//����LOGO
		URL image = LoginUI.class.getClassLoader().getResource("header.jpg");
		imageLogo = new JLabel(new ImageIcon(image));
		this.add(imageLogo, BorderLayout.NORTH);
		
		
		//�û���������  
        JPanel jp = new JPanel();  
      
        JPanel jpAccount = new JPanel();  
        jpAccount.add(new JLabel("             �ʺ�"));  
        username = new JTextField(12);  
        jpAccount.add(username);  
        jp.add(jpAccount);  
        jp.add(Box.createHorizontalStrut(1000)); 
        JPanel jpPass = new JPanel();  
        jpPass.add(new JLabel("             ����"));  
        password = new JPasswordField(12);  
        jpPass.add(password);  
        jp.add(jpPass);  
        
        jp.add(Box.createHorizontalStrut(1000));
        
        
        //������ɫ
        jpAccount.setBackground(new Color(255, 255, 220));
        jpPass.setBackground(new Color(255, 255, 220));
        jp.setBackground(new Color(255, 255, 220));
        

        //��¼��ť  
        registerButton = new JButton("ע��");
        jp.add(Box.createHorizontalStrut(1000));
        jp.add(registerButton);
       
        this.add(jp);  
     
        //��Ӽ�����
        registerButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		String usernameS = username.getText().trim();
        		String passwordS = String.valueOf(password.getPassword());
        		
        		if(service.register(usernameS, passwordS)) {
        			JOptionPane.showMessageDialog(null, "ע��ɹ�", "��ʾ", JOptionPane.OK_OPTION);
        		} else {
        			JOptionPane.showMessageDialog(null, "ע��ʧ��", "����", JOptionPane.OK_OPTION);
        		}
        	}
        });
        
        
        //���ô�������
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
