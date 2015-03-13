package uk.ac.abertay.eduapp.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.ftpserver.ftplet.FtpException;

import uk.ac.abertay.eduapp.SFTPServer;

/**
 * Application Lifecycle Listener implementation class ServerInit
 *
 */
public class ServerInit implements ServletContextListener {


    public ServerInit() {
        // TODO Auto-generated constructor stub
    }



    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }



    public void contextInitialized(ServletContextEvent arg0)  { 
    	System.out.println("----------------------------------> server init...");
    	try {
			new SFTPServer().init();
		} catch (FtpException e) {
			e.printStackTrace();
		}
    }
	
}
