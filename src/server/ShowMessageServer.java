package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ShowMessageServer {

	ServerSocket server;
	static int clientNum = 0;
	
	// 存放与服务器连接上的对应的Socket，作用是保存服务器与客户端之间的流，便于服务器给每个客户端进行回发消息
	List<Socket> clientConnection = new ArrayList<Socket>();

	public ShowMessageServer() {
		try {
			server = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 内部类，监听客户端是否有连接到服务器，并将此客户端的Socket传递给HandleSocket进行处理，同时将client存放到List中，即clientConnection中
	public class SocketListener implements Runnable {
		public void run() {
			Socket client;
			try {
				while (true) {
					client = server.accept();
					// 连接上一个就压入List中，即clientConnection中
					clientConnection.add(client);
					
					//接收并发布消息
					HandleSocket hs = new HandleSocket(client);
					// 连接上就让HandleSocket去处理
					new Thread(hs).start();
				}
			} catch (IOException e) {
				System.out.println("客户连接服务器失败");
			}
		}
	}

	// 内部类 处理一个Socket,接收一个Client发送过来的消息，并且服务器原封不动的返回给所有客户端，客户端对消息进行过滤
	class HandleSocket implements Runnable {
		Socket client;
		
		HandleSocket(Socket client) {
			this.client = client;
		}
		
		public void run() {
			try {
				clientNum++;
				// 启用输入流
				InputStream is = client.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				System.out.println("第" + clientNum + "个客户端连接进入服务器");
			
				
				boolean flag = true;
				String str;
				do {
					// 对用户发来的消息进行群发给客户端
					str = br.readLine();
					System.out.println("接受到一个客户端消息：" + str);
					for (int i = 0; i < clientConnection.size(); i++) {
						Socket client = clientConnection.get(i);
						OutputStream os = client.getOutputStream();
						PrintStream ps = new PrintStream(os);
						ps.println(str);
					}
				} while (flag);
				client.close();
			} catch (IOException e) {
				System.out.println("有一个客户断开与服务器的连接");
			}
		}
	}
	

}
