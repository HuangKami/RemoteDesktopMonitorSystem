package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.zip.ZipOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RobotClient {
	public RobotClient() {
		try {
			//ͨ��Socket��ȡ���ݵ�������
			Socket client = new Socket("127.0.0.1", 10000);
			
			//����������
			DataInputStream dis = new DataInputStream(client.getInputStream());
			
			//�������
			JFrame jframe = new JFrame();
			//����ر�Ҫ�رս���
			jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
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
			JLabel backImage = new JLabel();
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
			
			//ѭ����ȡͼƬ
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
				jframe.repaint();
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new RobotClient();
	}
	
}
