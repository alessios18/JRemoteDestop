/**
 * 
 */
package com.alessiosegantin.JRemoteDesktop.client.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.alessiosegantin.JRemoteDesktop.client.ui.Listener.ConnectionListener;

/**
 * @author alessio.segantin
 *
 */
public class MainFrame extends JFrame {
	JButton connect = null;
	JTextField ip = null;
	JTextField port = null;
	
	JPanel screen = null;
	
	public MainFrame() {
		initializeUI();
	}
	
	public void initializeUI() {
		this.setSize(400, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ip = new JTextField("127.0.0.1");
		port = new JTextField("1818");
		connect = new JButton("Connect");
		connect.addActionListener(new ConnectionListener(this));
		JPanel conn = new JPanel();
		conn.setLayout(new GridLayout(0, 5));
		conn.add(new JLabel("IP:PORT "));
		conn.add(ip);
		conn.add(new JLabel(" : "));
		conn.add(port);
		conn.add(connect);
		this.setLayout(new BorderLayout());
		this.add(conn,BorderLayout.NORTH);
		screen = new JPanel();
		this.add(screen, BorderLayout.CENTER);
	}
	
	public String getIp(){
		return ip.getText();
	}
	
	public int getPort() {
		return Integer.parseInt(port.getText());
	}
	

	public JPanel getScreen() {
		return screen;
	}
	
	public JButton getConnect() {
		return connect;
	}

}
