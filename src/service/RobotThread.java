package service;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import client.RobotClient;

import com.sun.image.codec.jpeg.*;

@SuppressWarnings("restriction")
public class RobotThread {
	private RobotClient robotClient;  
	private Dimension screenSize;  
    private Rectangle rectangle;  
    private Robot robot;
	JPEGImageEncoder encoder;
	DataOutputStream dos;
	
	/*public RobotThread(RobotClient robotClient) {
		try {
			this.robotClient = robotClient;
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
	        rectangle = new Rectangle(screenSize);
	        dos = new DataOutputStream(this.robotClient.getClientSocket().getOutputStream());
            robot = new Robot();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	
	public void run() {
        while (true) {  
            try { 
                BufferedImage image = robot.createScreenCapture(rectangle);// 捕获制定屏幕矩形区域  
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
                encoder.encode(image);
                byte[] data = baos.toByteArray();
                dos.write(data);
                dos.flush();
                Thread.sleep(50);
            } catch (Exception e) {  
                e.printStackTrace();  
            }
        }  
    }  */
	
}
