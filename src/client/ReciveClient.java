package client;

import java.io.DataInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ReciveClient implements Runnable {
	Socket socket;
	public ReciveClient() {
		try {
			socket = new Socket("127.0.0.1", 8585);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		try {
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			while(true) {
				if(dis.readUTF().equals("send")) {
					int recive = JOptionPane.showConfirmDialog(null, "是否接收文件", "提示", JOptionPane.YES_NO_OPTION);
					if(recive == JOptionPane.YES_OPTION) {
						String fileName = JOptionPane.showInputDialog("保存文件到");
						File file = new File(fileName);
						file.createNewFile();	
						RandomAccessFile raf = new RandomAccessFile(file, "rw");
						byte[] buf = new byte[100];
						int num;
						while((num=dis.read(buf)) >= 100) {
							raf.write(buf, 0, num);
							raf.skipBytes(num);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
