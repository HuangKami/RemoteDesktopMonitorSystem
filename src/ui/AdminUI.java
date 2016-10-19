package ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import server.DisplayServer;
import server.MonitorServer;
import server.ShowMessageServer;
import dao.UserDao;



/*
 * 用户主界面
 * @author Kami
 */
public class AdminUI extends JFrame implements ActionListener, Runnable {
	
	private static final long serialVersionUID = 1L;

	private JButton b1, b2, b3, b4, b5;
	private DisplayServer display;
	private ShowMessageServer message;
	private MonitorServer monitor;
	private DataOutputStream dos;
	private ArrayList<Socket>  list = new ArrayList<Socket>();
	private String str;
	
	public AdminUI() {
		b1 = new JButton("聊天");
		b2 = new JButton("直播");
		b3 = new JButton("监控");
		b4 = new JButton("文件传输");
		b5 = new JButton("用户注册");
		
		JPanel jp = new JPanel();
		jp.add(b1);
		jp.add(b2);
		jp.add(b3);
		jp.add(b4);
		jp.add(b5);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		
		if(display == null) {
			display = new DisplayServer();
			new Thread(display.new SocketListener()).start();
		}
		
		if(message == null) {
			message = new ShowMessageServer();
			new Thread(message.new SocketListener()).start();
		}
		
		if(monitor == null) {
			monitor = new MonitorServer();
			new Thread(monitor.new SocketListener()).start();
		}
		
		this.add(jp);
		this.setTitle("Admin");
		this.setSize(280, 110);
		this.setLocation(900, 40);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		new Thread(new ListHand()).start();
		///new Thread(new MonitorUI(str)).start();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b1) {
			new ChatUI("admin").setVisible(true);
		}
		
		if(e.getSource() == b2) {
			try {
				for (int i = 0; i < list.size(); i++) {
					Socket socket = list.get(i);
					dos = new DataOutputStream(
							socket.getOutputStream());
					dos.writeUTF("Yes");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}
		
		if(e.getSource() == b3) {
			try {
				for (int i = 0; i < list.size(); i++) {
					Socket socket = list.get(i);
					dos = new DataOutputStream(
							socket.getOutputStream());
					dos.writeUTF("KKK");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}
		
		if(e.getSource() == b4) {
			
		}
		
		if(e.getSource() == b5) {
			new RegisterUI();
		}
	}


	@Override
	public void run() {
		try {
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(7777);
			while(true) {
				Socket socket = ss.accept();
				list.add(socket);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	private class ListHand implements Runnable {
		
		public void run() {
			while(true) {
				for (int i = 0; i < list.size(); i++) {
					try {
						Socket s = list.get(i);
						DataInputStream dis = new DataInputStream(
								s.getInputStream());
						str = dis.readUTF();
						UserDao userDao = new UserDao();
						if(userDao.findUser(str) != null) {
							JOptionPane.showMessageDialog(null, str + "举手", "提示", JOptionPane.OK_OPTION);
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
}
