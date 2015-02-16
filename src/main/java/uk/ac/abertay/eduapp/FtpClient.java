package uk.ac.abertay.eduapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.SaltedPasswordEncryptor;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

public class FtpClient {
	public static void main(String[] args) {
		try {
			createUser();
		} catch (FtpException e) {
			e.printStackTrace();
		}
	}

	public static void createUser() throws FtpException {
		String username = "ftp";
		String password = "ftp";
		String ftproot = "data";

		// prepares the user manager
		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
		File propFile = new File("myusers.properties");
		System.out.println("Directory ------------> " + propFile.getAbsolutePath());
		userManagerFactory.setFile(propFile);
		userManagerFactory.setPasswordEncryptor(new SaltedPasswordEncryptor());
		UserManager um = userManagerFactory.createUserManager();
		// set up my user
		BaseUser user = new BaseUser();
		user.setName(username);
		user.setPassword(password);
		user.setHomeDirectory(ftproot);
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new WritePermission());
		user.setAuthorities(authorities);

		um.save(user);
		// adds the user
		// FtpServerThread.setUserManager(um);
	}
}
