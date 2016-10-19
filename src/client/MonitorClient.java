package client;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import com.sun.image.codec.jpeg.*;
public class MonitorClient {
	private Socket client;
	private String clientName;

	
	public MonitorClient(String clientName) {
		try {
			this.clientName = clientName;
			client = new Socket("127.0.0.1", 4444);
			DataOutputStream doc = new DataOutputStream(
					client.getOutputStream());
			ScreenThread screenThread = new ScreenThread(doc, this.clientName);
			screenThread.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class ScreenThread extends Thread{
	
	//��������������ϰ�ͼƬ���
	private DataOutputStream dataOut;
	private String clientName;
	//���캯��
	public ScreenThread(DataOutputStream dataOut, String clientName){
		this.dataOut = dataOut;
		this.clientName = clientName;
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
			dataOut.writeUTF(clientName);
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
				//Thread.sleep(20);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}