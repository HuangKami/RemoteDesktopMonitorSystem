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
	
	// ���������������ϵĶ�Ӧ��Socket�������Ǳ����������ͻ���֮����������ڷ�������ÿ���ͻ��˽��лط���Ϣ
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

	// �ڲ��࣬�����ͻ����Ƿ������ӵ��������������˿ͻ��˵�Socket���ݸ�HandleSocket���д���ͬʱ��client��ŵ�List�У���clientConnection��
	public class SocketListener implements Runnable {
		public void run() {
			Socket client;
			try {
				while (true) {
					client = server.accept();
					// ������һ����ѹ��List�У���clientConnection��
					clientConnection.add(client);
				}
			} catch (IOException e) {
				System.out.println("�ͻ����ӷ�����ʧ��");
			}
		}
	}
	

}
