package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.MonitorClient;




public class UserUI extends JFrame implements ActionListener, Runnable {

	private static final long serialVersionUID = 1L;
	private String clientName;
	private JButton b1 = new JButton("聊天");
	private JButton b2 = new JButton("举手");
	private JPanel jp = new JPanel();
	private Socket socket;
	
	public UserUI(final String clientName) {
		this.clientName = clientName;
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		jp.add(b1);
		jp.add(b2);
		this.add(jp);
		
		this.setTitle(clientName);
		this.setSize(200, 65);
		this.setLocation(1100, 40);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
	}
	
	public void connect() {
		try {
			socket = new Socket("127.0.0.1", 7777);
			DataInputStream dis = new DataInputStream(
					socket.getInputStream());
			while(true) {
				
				String str = dis.readUTF();
				
				if("Yes".equals(str)) {
					new Thread(new DisplayUI()).start();
				}
			
				if("KKK".equals(str)) {
					new MonitorClient(clientName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b1) {
			new ChatUI(clientName).setVisible(true);
		}
		
		if(e.getSource() == b2) {
			try {
				DataOutputStream dos = new DataOutputStream(
						socket.getOutputStream());
				dos.writeUTF(clientName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		connect();
	}	
}
