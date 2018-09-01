/**
 * 
 */
package com.alessiosegantin.JRemoteDesktop.server.manager;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * @author alessio.segantin
 *
 */
public class ScreenStream extends Thread {
	static Logger logger = Logger.getLogger(ScreenStream.class);
	Socket sc = null;
	Rectangle ret = null;
	Robot robot = null;
	OutputStream oos=null;

	private boolean isRunning = true;

	public ScreenStream(Socket sc,Rectangle ret,Robot robot) {
		this.sc = sc;
		this.ret=ret;
		this.robot=robot;
		start();
	}

	public void run() {
		try{
			oos=sc.getOutputStream();
			while(isRunning){
				BufferedImage image=robot.createScreenCapture(ret);
				try{
					OutputStream outputStream = sc.getOutputStream();
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			        ImageIO.write(image, "jpg", byteArrayOutputStream);

			        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
			        outputStream.write(size);
			        outputStream.write(byteArrayOutputStream.toByteArray());
			        outputStream.flush();
				}catch(IOException ex){
					logger.error(ex.getMessage(), ex);
					isRunning = false;
				}
				try{
					Thread.sleep(100);
				}catch(InterruptedException e){
					logger.error(e.getMessage(), e);
					isRunning = false;
				}
			}
		}catch(IOException ex){
			logger.error(ex.getMessage(), ex);
		}
	}

	public void stopStream() {
		isRunning = false;
	}
}
