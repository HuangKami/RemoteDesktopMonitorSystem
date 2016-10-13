package service;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sun.image.codec.jpeg.JPEGCodec;

@SuppressWarnings("restriction")
public class RobotThread implements Runnable {
	private Dimension screenSize;  
    private Rectangle rectangle;  
    private Robot robot;
	
	public RobotThread() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
        rectangle = new Rectangle(screenSize);
        try {  
            robot = new Robot();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	
	public void run() {
		ZipOutputStream os = null;  
        Socket socket = null;  
        while (true) {  
            try {  
                socket = new Socket("127.0.0.1", 8888);
                BufferedImage image = robot.createScreenCapture(rectangle);// 捕获制定屏幕矩形区域  
                os = new ZipOutputStream(socket.getOutputStream());// 加入压缩流  
  
                os.setLevel(9);  
                os.putNextEntry(new ZipEntry("test.jpg"));  
                JPEGCodec.createJPEGEncoder(os).encode(image);// 图像编码成JPEG  
                os.close();  
                Thread.sleep(50);
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                if (os != null) {  
                    try {  
                        os.close();  
                    } catch (Exception ioe) {  
                    }  
                }  
                if (socket != null) {  
                    try {  
                        socket.close();  
                    } catch (IOException e) {  
                    }  
                }  
            }  
        }  
    }  
	
}
