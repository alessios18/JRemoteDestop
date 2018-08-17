/**
 * 
 */
package com.alessiosegantin.JRemoteDesktop.server.manager;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.log4j.Logger;

/**
 * @author alessio.segantin
 *
 */
public class ServerManager extends Thread {
	static Logger logger = Logger.getLogger(ServerManager.class);

	private boolean isRunning = true;
	private String width="";
	private String height="";
	private ScreenStream ss = null;
	private DataOutputStream controlStream = null;

	ServerSocket server = null;

	public ServerManager(int port) throws IOException {
		super();
		server=new ServerSocket(port);
		server.setSoTimeout(2000);
		isRunning = true;
	}

	public void run() {
		Robot robot = null;
		Rectangle rectangle = null;
		GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gDev = gEnv.getDefaultScreenDevice();

		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		int w = (int) ((100/dim.getWidth())*10);
		int h = (int) ((100/dim.getHeight())*10);
		width=""+w;
		height=""+h;
		rectangle=new Rectangle(dim);
		try {
			robot=new Robot(gDev);
			logger.info("Awaiting Connection from Client");
			while(isRunning){
				try {
					Socket sc=server.accept();
					logger.info("Connected: "+ sc.getInetAddress().getHostName());
					controlStream = new DataOutputStream(sc.getOutputStream());
					controlStream.writeUTF(width);
					controlStream.writeUTF(height);
					ss = new ScreenStream(sc, rectangle, robot);
				} catch (IOException e) {
					if(!(e instanceof SocketTimeoutException)) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage(), e1);
		}
	}

	public void stopConnection() {
		if(ss != null) {
			ss.stopStream();
		}
		isRunning=false;
		this.interrupt();
	}
}
