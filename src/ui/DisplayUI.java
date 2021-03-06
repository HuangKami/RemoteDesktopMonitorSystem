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
			
			//数据输入流
			dis = new DataInputStream(client.getInputStream());
			
			//创建面板
			jframe = new JFrame();
			//窗体关闭要关闭进程
			jframe.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
					jframe.setVisible(false);
				}
			});
			
			jframe.setTitle("远程桌面监控系统客户端");
			//创建窗口
			jframe.setSize(1024,768);
			//设置窗体出现位置
			jframe.setLocation(350,0);
			
			//读取服务器(教师端)屏幕分辨率
			double height = dis.readDouble();
			double width = dis.readDouble();
			
			Dimension dimensionServer = new Dimension((int)height,(int)width);
			
			//设置
			jframe.setSize(dimensionServer);
			//将服务端的照片作为背景
			backImage = new JLabel();
			JPanel panel = new JPanel();
			
			//需要制作滚动条
			JScrollPane scrollPane = new JScrollPane(panel);
			panel.setLayout(new FlowLayout());
			
			//add背景图片
			panel.add(backImage);
			//add滚动条
			jframe.add(scrollPane);
			//设置窗体置顶
			jframe.setAlwaysOnTop(true);
			//设置窗体可见
			jframe.setVisible(true);
			
			
			
		}catch(Exception e){
			e.getStackTrace();
		}
	}



	@Override
	public void run() {
		//循环读取图片
		while(true){
			try {//先读取数据大小
				int len = dis.readInt();
				//获取的参数放置到一个数组里面
				imageData = new byte[len];
				//读取图片的数据流
				dis.readFully(imageData);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//完整地读取数据的二进制数组
			ImageIcon image = new ImageIcon(imageData);
			backImage.setIcon(image);
			
			//重新绘制面板
			jframe.repaint();
			
		}
	}
	
}
