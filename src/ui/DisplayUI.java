package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DisplayUI implements Runnable {
	JFrame jframe; 
	DataInputStream dis;
	JLabel backImage;
	byte[] imageData;
	public static void main(String[] args) {
		new Thread(new DisplayUI()).start();
	}
	
	
	
	public DisplayUI() {
		try{
			@SuppressWarnings("resource")
			Socket client = new Socket("127.0.0.1", 6666);
			
			//����������
			dis = new DataInputStream(client.getInputStream());
			
			//�������
			jframe = new JFrame();
			//����ر�Ҫ�رս���
			jframe.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
					jframe.setVisible(false);
				}
			});
			
			jframe.setTitle("Զ��������ϵͳ�ͻ���");
			//��������
			jframe.setSize(1024,768);
			//���ô������λ��
			jframe.setLocation(350,0);
			
			//��ȡ������(��ʦ��)��Ļ�ֱ���
			double height = dis.readDouble();
			double width = dis.readDouble();
			
			Dimension dimensionServer = new Dimension((int)height,(int)width);
			
			//����
			jframe.setSize(dimensionServer);
			//������˵���Ƭ��Ϊ����
			backImage = new JLabel();
			JPanel panel = new JPanel();
			
			//��Ҫ����������
			JScrollPane scrollPane = new JScrollPane(panel);
			panel.setLayout(new FlowLayout());
			
			//add����ͼƬ
			panel.add(backImage);
			//add������
			jframe.add(scrollPane);
			//���ô����ö�
			jframe.setAlwaysOnTop(true);
			//���ô���ɼ�
			jframe.setVisible(true);
			
			
			
		}catch(Exception e){
			e.getStackTrace();
		}
	}



	@Override
	public void run() {
		//ѭ����ȡͼƬ
		while(true){
			try {//�ȶ�ȡ���ݴ�С
				int len = dis.readInt();
				//��ȡ�Ĳ������õ�һ����������
				imageData = new byte[len];
				//��ȡͼƬ��������
				dis.readFully(imageData);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//�����ض�ȡ���ݵĶ���������
			ImageIcon image = new ImageIcon(imageData);
			backImage.setIcon(image);
			
			//���»������
			jframe.repaint();
			
		}
	}
	
}
