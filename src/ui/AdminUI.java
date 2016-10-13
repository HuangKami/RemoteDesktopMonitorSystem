package ui;


import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import server.RobotSever;
import server.ShowMessageServer;


/*
 * 用户主界面
 * @author Kami
 */
public class AdminUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private static ImageIcon[] images = new ImageIcon[60];
	
	private JLabel label = new JLabel("KKK"); 
	
	public static void setImages(ImageIcon[] imagess) {
		images = imagess;
	}
	
	public AdminUI() {  
		
		new Thread(new ShowMessageServer().new SocketListener()).start();
		//new RobotSever();
    }  
  
    /*public void paint(Graphics g) {  
       super.paint(g); 
       System.out.println(1);
       if(images != null) {
    	   for(int i=0; i<images.length; i++) {
    		   if(images[i] != null) {
    			   label.setIcon(images[i]);
    		   }
           }
       }
    }

	public void run() {
		while(true) {
			repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}  */
}
	
