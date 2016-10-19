package ui;

import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import service.ShowOldMessageThread;
import client.ChatClient;

@SuppressWarnings("rawtypes")
public class ChatUI extends JFrame {
private static final long serialVersionUID = 1L;
	
	private TextArea oldMessageTextArea;
	private TextArea sendMessageTextArea;
	private JList userList;
	private JScrollPane userListPane;
	private JButton btSend;
	private JButton btClosed;
	private JButton upLine;
	private ChatClient chatClient;
	private String clientName;
	private Vector users;
	
	private Dimension screenSize;  

	public ChatUI(final String clientName) {
		this.clientName = clientName;
		chatClient = new ChatClient(clientName);
		
		this.setTitle("��ӭ����" + clientName);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		screenSize = tk.getScreenSize();
		int x = (int) screenSize.getWidth();
		int y = (int) screenSize.getHeight();
		this.setBounds((x - 600) / 2, (y - 600) / 2, 600, 600);
		
		
		this.setResizable(false);
		this.setLayout(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				setVisible(false);
			}
		});

		// �����Ѿ�����ȥ����Ϣ���ڵ�����
		oldMessageTextArea = new TextArea();
		oldMessageTextArea.setBounds(0, 0, 390, 360);

		// ����׼��������Ϣ���ڵ�����
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
		

		// ����<����>��ť������
		upLine = new JButton("����");
		upLine.setBounds(0, 530, 70, 30);
		
		// <����>��ť�ĵ���¼�
		upLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});

		// ����<����>��ť������
		btSend = new JButton("����");
		btSend.setBounds(240, 530, 70, 30);
		
		// ע��<����>��ť�ĵ���¼�
		btSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		
		// ����<�ر�>��ť������
		btClosed = new JButton("�ر�");
		btClosed.setBounds(320, 530, 70, 30);
		
		// ע��<�ر�>��ť�ĵ���¼�
		btClosed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		
		// �����û��б�JList����
		userList = new JList();
		userList.setBounds(400, 20, 200, 600);
		users = new Vector();
		

		// �����û��б�JScrollPane������
		userListPane = new JScrollPane(userList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		userListPane.setBounds(400, 0, 200, 600);

		// new һ��ChatUIʱ�����߳���������ȡ�������˻ط�����Ϣ
		new Thread(new ShowOldMessageThread(chatClient, users, userList, oldMessageTextArea)).start();
		System.out.println(123);
		// �����������ӵ�������
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
		chatClient.getPs().println(clientName + "˵��" + s);
		sendMessageTextArea.setText("");
	}
}
