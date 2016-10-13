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
			// �����ͻ���sSocket
			clientSocket = new Socket("127.0.0.1", 9999);
			System.out.println(clientName + "���ӷ������ɹ�");
			// ���������
			OutputStream os = clientSocket.getOutputStream();
			ps = new PrintStream(os);

			// ����������
			InputStream is = clientSocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			
			
		} catch (Exception e) {
			System.out.println("��δ������������");
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
