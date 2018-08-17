/**
 * 
 */
package com.alessiosegantin.JRemoteDesktop.client.manager;

import java.awt.Graphics;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author alessio.segantin
 *
 */
public class ScreenStreem extends Thread {
	private JPanel panel = null;
	private boolean isRunning = true;
	InputStream oin = null;
	Image image1 = null;
	Socket sc = null;
	
	public ScreenStreem(Socket sc,JPanel p) throws IOException {
		this.sc = sc;
		this.panel = p;
		this.oin = sc.getInputStream();
	}
	
	public void run(){
		try{
			//Read screenshots of the client and then draw them
			while(isRunning){
				byte[] bytes = new byte[1024*1024];
				int count = 0;
//				do{
//					count+=oin.read(bytes,count,bytes.length-count);
//				}while(!(count>4 && bytes[count-2]==(byte)-1 && bytes[count-1]==(byte)-39));

				image1 = ImageIO.read(new ByteArrayInputStream(bytes));
				image1 = image1.getScaledInstance(panel.getWidth(),panel.getHeight(),Image.SCALE_FAST);

				//Draw the received screenshots

				Graphics graphics = panel.getGraphics();
				graphics.drawImage(image1, 0, 0, panel.getWidth(), panel.getHeight(), panel);
			}

		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	public boolean isRunning() {
		return isRunning;
	}
	
	public void stopStream() {
		isRunning = false;
	}
	
}
