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
				System.out.println("客户连接服务器失败");
			}
		}
	
	}
}

class ScreenThread extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	//数据输出流，不断把图片输出
	private DataInputStream dis;
	
	//构造函数
	public ScreenThread(DataInputStream dis){
		this.dis = dis;
	}
	
	//调用线程
	//启动线程屏幕开始传输
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
				//先读取数据大小
				int len = dis.readInt();
				//获取的参数放置到一个数组里面
				byte[] imageData = new byte[len];
				
				//读取图片的数据流
				dis.readFully(imageData);
				
				//完整地读取数据的二进制数组
				ImageIcon image = new ImageIcon(imageData);
				backImage.setIcon(image);
				
				//重新绘制面板
				this.repaint();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
