package server;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


import com.sun.image.codec.jpeg.*;


public class DisplayServer {
	
	ServerSocket server;
	
	public DisplayServer() {
		try {
			server = new ServerSocket(6666);
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
					
					OutputStream os = client.getOutputStream();
					DataOutputStream doc = new DataOutputStream(os);
					
					new Thread(new ScreenThread(doc)).start();
				}
			} catch (IOException e) {
				System.out.println("�ͻ����ӷ�����ʧ��");
			}
		}
}


class ScreenThread extends Thread{
	
	//��������������ϰ�ͼƬ���
	private DataOutputStream dataOut;
	
	//���캯��
	public ScreenThread(DataOutputStream dataOut){
		this.dataOut = dataOut;
	}
	
	//�����߳�
	//�����߳���Ļ��ʼ����
	@SuppressWarnings("restriction")
	public void run(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dm = tk.getScreenSize();
		
		try{
			
			//��ȡ��Ļ�ķֱ���
			//��ȡ�������(��ʦ��)���͵��ͻ���(ѧ����)
			//�ļ������
			dataOut.writeDouble(dm.getHeight());
			dataOut.writeDouble(dm.getWidth());
			dataOut.flush();
			
			//���������С
			//Rectangleָ������ռ��һ������ͨ������ռ��е�Rectangle�������Ϸ��ĵ�(x,y)����Ⱥ͸߶ȿ��Զ����������
			Rectangle rec = new Rectangle(dm);
			
			//ͨ�������˻�ȡ����
			Robot robot = new Robot();
			
			
			//����ѭ��
			while(true){
				//ͨ�������˽�ȡ����ͼƬ
				BufferedImage bufImg = robot.createScreenCapture(rec);
				//�ö��������������洢ת�����ͼƬ,����ѹ��
				//ѹ����Ƭ�ڴ��С,���������Զ����Ƶ��ص�������
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				//ѹ��
				//JPEGImageEncoder:���ڰ�ͼƬת���ɶ����ƽ���ѹ��
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
				encoder.encode(bufImg); 
				
				//������ͼƬ��ȡת���ɶ���������
				byte[] data = baos.toByteArray();
				
				//��ͼƬת��ɶ����������Ժ�Ҫ������ĳ��ȷ��͵��ͻ���(ѧ����)
				dataOut.writeInt(data.length);
				dataOut.write(data);
				dataOut.flush();
				
				//�ӳ�20����
				Thread.sleep(20);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	}
}