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
				// 接收群聊服务器端回发过来的消息
				String serverOutput = chatClient.getBr().readLine() + "\r\n";
				if (!(serverOutput.substring(serverOutput
								.indexOf("：") + 1).equals("\r\n"))) {
					String s = serverOutput.replace('说', ' ');
					
					oldMessageTextArea.append(s);
				}

				// 添加客户端的用户在线列表
				if ((serverOutput.indexOf("说") != -1)) {
					String listName = serverOutput.substring(0,
							serverOutput.indexOf('说'));
					// 如果JList中有相同名字的用户，则不添加，否则添加
					if (!users.contains(listName)) {
						System.out.println("用户" + listName + "上线了");
						users.add(listName);
						userList.setListData(users);
					}
				}
			} catch (IOException e1) {
				System.out.println("读取服务器端消息出错");
			}
		}
	}
}