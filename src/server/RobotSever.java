package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class RobotSever {
	
	ServerSocket server;
	
	Map<String, Socket> clientConnection = new HashMap<String, Socket>();
	
	public RobotSever() {
		try {
			server = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private class SocketListener implements Runnable {
		public void run() {
			Socket client;
			try {
				while (true) {
					client = server.accept();
					// 连接上一个就压入List中，即clientConnection中
					clientConnection.put(client.getInetAddress()+""
							,client);
					
				}
			} catch (IOException e) {
				System.out.println("客户连接服务器失败");
			}
		}
	}
	
}
