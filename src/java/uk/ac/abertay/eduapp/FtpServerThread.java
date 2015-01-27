package uk.ac.abertay.eduapp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;

/**
 * 
 * @author Sam Okide
 *
 */
public class FtpServerThread {

	public static void main(String[] args) {
		try {
			startServer();
		} catch (FtpException e) {
			e.printStackTrace();
		}
	}

	public static void startServer() throws FtpException {
		FtpServerFactory serverFactory = new FtpServerFactory();
		ListenerFactory factory = new ListenerFactory();
		// set the port of the listener
		factory.setPort(2221);
		// define SSL configuration
		// SslConfigurationFactory ssl = new SslConfigurationFactory();
		// ssl.setKeystoreFile(new File("src/test/resources/ftpserver.jks"));
		// ssl.setKeystorePassword("password");
		// // set the SSL configuration for the listener
		// factory.setSslConfiguration(ssl.createSslConfiguration());
		// factory.setImplicitSsl(true);
		// // replace the default listener
		serverFactory.addListener("default", factory.createListener());
		// PropertiesUserManagerFactory userManagerFactory = new
		// PropertiesUserManagerFactory();
		// userManagerFactory.setFile(new File("myusers.properties"));
		// serverFactory.setUserManager(userManagerFactory.createUserManager());
		// start the server
		FtpServer server = serverFactory.createServer();
		server.start();
	}
}
