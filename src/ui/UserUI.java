package ui;

import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import service.RobotThread;
import service.ShowOldMessageThread;
import client.RobotClient;
import client.ShowMessageClient;


@SuppressWarnings("rawtypes")
public class UserUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private TextArea oldMessageTextArea;
	private TextArea sendMessageTextArea;
	private JList userList;
	private JScrollPane userListPane;
	private JButton btSend;
	private JButton btClosed;
	private JButton upLine;
	private ShowMessageClient showMessageClient;
	private RobotClient robotClient;
	private String clientName;
	private Vector users;
	
	private Dimension screenSize;  

	public UserUI(final String clientName) {
		this.clientName = clientName;
		showMessageClient = new ShowMessageClient(clientName);
		
	//	new RobotClient();
		
		this.setTitle("欢迎您：" + clientName);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		screenSize = tk.getScreenSize();
		int x = (int) screenSize.getWidth();
		int y = (int) screenSize.getHeight();
		this.setBounds((x - 600) / 2, (y - 600) / 2, 600, 600);
		
		
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 设置已经发出去的消息窗口的属性
		oldMessageTextArea = new TextArea();
		oldMessageTextArea.setBounds(0, 0, 390, 360);

		// 设置准备发送消息窗口的属性
		sendMessageTextArea = new TextArea(3, 3);
		sendMessageTextArea.setBounds(0, 380, 390, 140);
		
		sendMessageTextArea.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_ENTER) {
					send();
				}
			}
		});
		

		// 设置<上线>按钮的属性
		upLine = new JButton("上线");
		upLine.setBounds(0, 530, 70, 30);
		
		// <上线>按钮的点击事件
		upLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});

		// 设置<发送>按钮的属性
		btSend = new JButton("发送");
		btSend.setBounds(240, 530, 70, 30);
		
		// 注册<发送>按钮的点击事件
		btSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		
		// 设置<关闭>按钮的属性
		btClosed = new JButton("关闭");
		btClosed.setBounds(320, 530, 70, 30);
		
		// 注册<关闭>按钮的点击事件
		btClosed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		
		// 设置用户列表JList属性
		userList = new JList();
		userList.setBounds(400, 20, 200, 600);
		users = new Vector();
		

		// 设置用户列表JScrollPane的属性
		userListPane = new JScrollPane(userList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		userListPane.setBounds(400, 0, 200, 600);
		// new 一个UserUI时，此线程启动，获取服务器端回发的消息
		new Thread(new ShowOldMessageThread(showMessageClient, users, userList, oldMessageTextArea)).start();
		
		// 将所有组件添加到窗体上
		this.add(oldMessageTextArea);
		this.add(sendMessageTextArea);
		this.add(btSend);
		this.add(upLine);
		this.add(btClosed);
		this.add(userListPane);
	}
	
	private void send() {
		upLine.setEnabled(false);
		String s1 = sendMessageTextArea.getText();
		String s = s1.replaceAll("\r\n", "");
		showMessageClient.getPs().println(clientName + "说：" + s);
		sendMessageTextArea.setText("");
	}
}
