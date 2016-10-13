package client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;


public class ShowMessageClient {
	private PrintStream ps;
	private BufferedReader br;
	private Socket clientSocket;

	public ShowMessageClient(String clientName) {
		try {
			// 创建客户端sSocket
			clientSocket = new Socket("127.0.0.1", 9999);
			System.out.println(clientName + "连接服务器成功");
			// 启用输出流
			OutputStream os = clientSocket.getOutputStream();
			ps = new PrintStream(os);

			// 启用输入流
			InputStream is = clientSocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			
			
		} catch (Exception e) {
			System.out.println("您未能连接上主机");
		} 

	}

	public PrintStream getPs() {
		return ps;
	}

	public void setPs(PrintStream ps) {
		this.ps = ps;
	}

	public BufferedReader getBr() {
		return br;
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}
	
	
}
