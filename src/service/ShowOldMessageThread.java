package service;

import java.awt.TextArea;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JList;

import client.Client;

@SuppressWarnings("rawtypes")
public class ShowOldMessageThread implements Runnable {
	
	private Client client;
	private Vector users;
	private JList userList;
	private TextArea oldMessageTextArea;
	
	public ShowOldMessageThread(Client client, Vector users, JList userList, TextArea oldMessageTextArea) {
		this.client = client;
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
				String serverOutput = client.getBr().readLine() + "\r\n";
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