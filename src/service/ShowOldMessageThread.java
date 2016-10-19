package service;

import java.awt.TextArea;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JList;

import client.ChatClient;

@SuppressWarnings("rawtypes")
public class ShowOldMessageThread implements Runnable {
	
	private ChatClient chatClient;
	private Vector users;
	private JList userList;
	private TextArea oldMessageTextArea;
	
	public ShowOldMessageThread(ChatClient chatClient, Vector users, JList userList, TextArea oldMessageTextArea) {
		this.chatClient = chatClient;
		this.users = users;
		this.userList = userList;
		this.oldMessageTextArea = oldMessageTextArea;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		boolean flag = true;
		while (flag) {
			try {
				// ����Ⱥ�ķ������˻ط���������Ϣ
				String serverOutput = chatClient.getBr().readLine() + "\r\n";
				if (!(serverOutput.substring(serverOutput
								.indexOf("��") + 1).equals("\r\n"))) {
					String s = serverOutput.replace('˵', ' ');
					
					oldMessageTextArea.append(s);
				}

				// ��ӿͻ��˵��û������б�
				if ((serverOutput.indexOf("˵") != -1)) {
					String listName = serverOutput.substring(0,
							serverOutput.indexOf('˵'));
					// ���JList������ͬ���ֵ��û�������ӣ��������
					if (!users.contains(listName)) {
						System.out.println("�û�" + listName + "������");
						users.add(listName);
						userList.setListData(users);
					}
				}
			} catch (IOException e1) {
				System.out.println("��ȡ����������Ϣ����");
			}
		}
	}
}