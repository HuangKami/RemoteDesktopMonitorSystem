package server;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class FileSendServer {

	ServerSocket server;
	static int clientNum = 0;
	
	// 存放与服务器连接上的对应的Socket，作用是保存服务器与客户端之间的流，便于服务器给每个客户端进行回发消息
	List<Socket> clientConnection = new ArrayList<Socket>();

	public FileSendServer() {
		try {
			server = new ServerSocket(8585);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String fileName) {
		try {
			FileInputStream fis = null;
			DataOutputStream dos = null;
			File file = new File(fileName);
			for(int i=0; i<clientConnection.size(); i++) {
				Socket client = clientConnection.get(i);
				fis = new FileInputStream(file);
				dos = new DataOutputStream(client.getOutputStream());
				dos.writeUTF("send");
				byte[] buf = new byte[100];
				int num = fis.read(buf);
				while(num > 0) {
					dos.write(buf, 0, num);
					dos.flush();
					num = fis.read(buf);
				}
			}
		} catch(Exception e) {
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
				}
			} catch (IOException e) {
				System.out.println("客户连接服务器失败");
			}
		}
	}
	

}
