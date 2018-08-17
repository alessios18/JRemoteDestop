/**
 * 
 */
package com.alessiosegantin.JRemoteDesktop.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.alessiosegantin.JRemoteDesktop.server.manager.ServerManager;
import com.alessiosegantin.JRemoteDesktop.server.utility.Utils;

/**
 * @author alessio.segantin
 *
 */
public class StartServer {
	public static Scanner sc;
	public static ServerManager serverManager;
	public static boolean isRunning = true;
	static Logger logger = Logger.getLogger(StartServer.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		Properties props = new Properties();
		try {
			ClassLoader classLoader = StartServer.class.getClassLoader();
			File file = new File(classLoader.getResource("logForJSettings.properties").getFile());
			props.load(new FileInputStream(file));
			PropertyConfigurator.configure(props);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
		}
		try {
			do{
				System.out.println("Insert the command:");
				System.out.println("\t- start");
				System.out.println("\t- stop");
				System.out.println("\t- exit");
				String command = sc.nextLine();
				if(Utils.COMMAND_START.equals(command)) {
					serverManager = new ServerManager(1818);
					serverManager.start();
				}else if(Utils.COMMAND_STOP.equals(command)) {
					if(serverManager != null) {
						serverManager.stopConnection();
					}
				}else if(Utils.COMMAND_EXIT.equals(command)) {
					if(serverManager != null) {
						serverManager.stopConnection();
					}
					isRunning = false;
				}
			}while(isRunning);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
