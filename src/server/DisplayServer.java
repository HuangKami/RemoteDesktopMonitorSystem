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
				System.out.println("客户连接服务器失败");
			}
		}
}


class ScreenThread extends Thread{
	
	//数据输出流，不断把图片输出
	private DataOutputStream dataOut;
	
	//构造函数
	public ScreenThread(DataOutputStream dataOut){
		this.dataOut = dataOut;
	}
	
	//调用线程
	//启动线程屏幕开始传输
	@SuppressWarnings("restriction")
	public void run(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dm = tk.getScreenSize();
		
		try{
			
			//截取屏幕的分辨率
			//获取到服务端(教师端)发送到客户端(学生端)
			//文件输出流
			dataOut.writeDouble(dm.getHeight());
			dataOut.writeDouble(dm.getWidth());
			dataOut.flush();
			
			//矩形区域大小
			//Rectangle指定坐标空间的一个区域，通过坐标空间中的Rectangle对象左上方的点(x,y)、宽度和高度可以定义这个区域
			Rectangle rec = new Rectangle(dm);
			
			//通过机器人获取数据
			Robot robot = new Robot();
			
			
			//不断循环
			while(true){
				//通过机器人截取本地图片
				BufferedImage bufImg = robot.createScreenCapture(rec);
				//用二进制数据流来存储转换后的图片,进行压缩
				//压缩照片内存大小,有利于提高远程视频监控的流畅度
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				//压缩
				//JPEGImageEncoder:用于把图片转换成二进制进行压缩
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
				encoder.encode(bufImg); 
				
				//将本地图片截取转换成二进制数组
				byte[] data = baos.toByteArray();
				
				//将图片转变成二进制数组以后，要将数组的长度发送到客户端(学生端)
				dataOut.writeInt(data.length);
				dataOut.write(data);
				dataOut.flush();
				
				//延迟20毫秒
				Thread.sleep(20);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	}
}