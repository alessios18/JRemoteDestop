/**
 * 
 */
package com.alessiosegantin.JRemoteDesktop.client.ui.Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.alessiosegantin.JRemoteDesktop.client.manager.ScreenStreem;
import com.alessiosegantin.JRemoteDesktop.client.ui.MainFrame;

/**
 * @author alessio.segantin
 *
 */
public class ConnectionListener implements ActionListener {
	static Logger logger = Logger.getLogger(ConnectionListener.class);
	MainFrame frame = null;
	ScreenStreem ss = null;
	Socket sc = null ;
	
	public ConnectionListener(MainFrame frame) {
		this.frame = frame;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(ss == null || (ss != null && !ss.isRunning())) {
			try {
				sc = new Socket(frame.getIp(), frame.getPort());
				ss = new ScreenStreem(sc, frame.getScreen());
				ss.start();
				frame.getConnect().setText("Disconnect");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				logger.error(e1.getMessage(), e1);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error(e1.getMessage(), e1);
			}
			
		}else {
			ss.stopStream();
			frame.getConnect().setText("Connect");
		}
	}

}
