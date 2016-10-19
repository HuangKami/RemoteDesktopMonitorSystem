package server;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;




public class MonitorServer {
	ServerSocket server;

	public MonitorServer() {
		try {
			server = new ServerSocket(4444);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class SocketListener implements Runnable {
		public void run() {
			Socket client;
			try {
				while(true) {
					client = server.accept();
					
					DataInputStream dis = new DataInputStream(
							client.getInputStream());
					
					new Thread(new ScreenThread(dis)).start();
				}
			} catch (IOException e) {
				System.out.println("�ͻ����ӷ�����ʧ��");
			}
		}
	
	}
}

class ScreenThread extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	//��������������ϰ�ͼƬ���
	private DataInputStream dis;
	
	//���캯��
	public ScreenThread(DataInputStream dis){
		this.dis = dis;
	}
	
	//�����߳�
	//�����߳���Ļ��ʼ����
	public void run(){
		try{
			
			double height = dis.readDouble();
			double width = dis.readDouble();
			String clientName = dis.readUTF();
			Dimension dimensionServer = new Dimension((int)height,(int)width);
			this.setSize(dimensionServer);
			JLabel backImage = new JLabel();
			JPanel panel = new JPanel();
			JScrollPane scrollPane = new JScrollPane(panel);
			panel.setLayout(new FlowLayout());
			panel.add(backImage);
			this.setTitle(clientName);
			this.add(scrollPane);
			this.setVisible(true);
			while(true){
				//�ȶ�ȡ���ݴ�С
				int len = dis.readInt();
				//��ȡ�Ĳ������õ�һ����������
				byte[] imageData = new byte[len];
				
				//��ȡͼƬ��������
				dis.readFully(imageData);
				
				//�����ض�ȡ���ݵĶ���������
				ImageIcon image = new ImageIcon(imageData);
				backImage.setIcon(image);
				
				//���»������
				this.repaint();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
