/**
 * 
 */
package com.alessiosegantin.JRemoteDesktop.client.manager;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.sound.midi.ControllerEventListener;
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
			DataInputStream controlStream = new DataInputStream(sc.getInputStream());
			String w,h;
			w = controlStream.readUTF();
			h = controlStream.readUTF();
			panel.getParent().setSize(Integer.parseInt(w)/2, Integer.parseInt(h)/2);
			InputStream inputStream = sc.getInputStream();
			BufferedImage image = null;
			//Read screenshots of the client and then draw them
			while(isRunning){

				System.out.println("Reading: " + System.currentTimeMillis());

				byte[] sizeAr = new byte[64];
				inputStream.read(sizeAr);
				int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

				try {
					byte[] imageAr = new byte[size];
					controlStream.readFully(imageAr);

					image = ImageIO.read(new ByteArrayInputStream(imageAr));
				}catch(Exception ex) {
					System.out.println(ex.getMessage());
				}
				//Draw the received screenshots
				if(image != null) {
					Graphics graphics = panel.getGraphics();
					graphics.drawImage(image, 0, 0, panel.getWidth(), panel.getHeight(), panel);
				}
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
